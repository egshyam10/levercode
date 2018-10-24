package com.accenture.dcsc.adp.portal.api.tracking.web.support;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.accenture.dcsc.adp.portal.api.tracking.web.tracking.UiTrackingEventItemController;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class PortalLinks {

    @Value("${rest.base-path}")
    private String baseUrl;
    
    public Link linkToSelf() {
        return new Link(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString());
    }
    /*
     * UI Event Links
     */
    public Link linkToTrackingResource() throws JsonProcessingException {
        return linkTo(methodOn(UiTrackingEventItemController.class).post(null)).withRel(Relations.TRACKING_REL);
    }
}
