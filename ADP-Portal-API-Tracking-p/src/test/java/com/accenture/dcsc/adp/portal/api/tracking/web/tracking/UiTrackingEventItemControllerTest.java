package com.accenture.dcsc.adp.portal.api.tracking.web.tracking;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.hateoas.Link;
import org.springframework.test.web.servlet.MvcResult;

import com.accenture.dcsc.adp.portal.api.tracking.AbstractFunctionalTest;
import com.accenture.dcsc.adp.portal.api.tracking.MvcResultUtils;
import com.accenture.dcsc.adp.portal.api.tracking.web.shyamsundar;
import com.accenture.dcsc.adp.portal.api.tracking.web.support.PortalMediaTypes;
import com.accenture.dcsc.adp.portal.api.tracking.web.support.Relations;

public class UiTrackingEventItemControllerTest extends AbstractFunctionalTest {

    @Test
    public void canLogJSONValueToSplunk() throws Exception {

        Link link = traverson.follow(//
                Relations.NS_UIEVENT_REL //
        ).withHeaders(shyamsundar.headers()).asLink();//

        Map<String, Object> expected = new HashMap<>();
        expected.put("action", "Click");
        expected.put("entity", "NavigationButton");
        expected.put("value", "home");
        expected.put("category", "global-navigation");

        mvc.perform(post(link.expand().getHref()).//
                with(shyamsundar.token()).//
                accept(PortalMediaTypes.HAL_JSON_VALUE).//
                contentType(PortalMediaTypes.HAL_JSON_VALUE).//
                content(objectMapper.writeValueAsString(expected))).//
                andExpect(status().isCreated());
    }
    
    @Test
    public void canLogJSONValueToSplunkWithAnnoymiseFieldSetToFalse() throws Exception {

        Link link = traverson.follow(//
                Relations.NS_UIEVENT_REL //
        ).withHeaders(shyamsundar.headers()).asLink();//

        Map<String, Object> expected = new HashMap<>();
        expected.put("action", "Click");
        expected.put("entity", "NavigationButton");
        expected.put("value", "home");
        expected.put("category", "global-navigation");
        expected.put("annoymise", false);

        mvc.perform(post(link.expand().getHref()).//
                with(shyamsundar.token()).//
                accept(PortalMediaTypes.HAL_JSON_VALUE).//
                contentType(PortalMediaTypes.HAL_JSON_VALUE).//
                content(objectMapper.writeValueAsString(expected))).//
                andExpect(status().isCreated());
    }
    
    @Test
    public void canLogJSONValueToSplunkWithAnnoymiseFieldSetToTrue() throws Exception {

        Link link = traverson.follow(//
                Relations.NS_UIEVENT_REL //
        ).withHeaders(shyamsundar.headers()).asLink();//

        Map<String, Object> expected = new HashMap<>();
        expected.put("action", "Click");
        expected.put("entity", "NavigationButton");
        expected.put("value", "home");
        expected.put("category", "global-navigation");
        expected.put("annoymise", true);

        mvc.perform(post(link.expand().getHref()).//
                with(shyamsundar.token()).//
                accept(PortalMediaTypes.HAL_JSON_VALUE).//
                contentType(PortalMediaTypes.HAL_JSON_VALUE).//
                content(objectMapper.writeValueAsString(expected))).//
                andExpect(status().isCreated());
    }

    @Test
    public void canHeadTrackingResource() throws Exception {

        Link link = traverson.follow(//
                Relations.NS_UIEVENT_REL //
        ).withHeaders(shyamsundar.headers()).asLink();//

        MvcResult result = mvc.perform(head(link.expand().getHref()).//
                with(shyamsundar.token()).//
                accept(PortalMediaTypes.HAL_JSON_VALUE).//
                contentType(PortalMediaTypes.HAL_JSON_VALUE)).//
                andExpect(status().isNoContent()).//
                andReturn();//

        Collection<Link> linkHeaders = MvcResultUtils.getLinkHeaders(result);
        assertThat(linkHeaders, hasItem(hasProperty(REL, equalTo(Relations.TRACKING_REL))));
    }
}
