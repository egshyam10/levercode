package com.accenture.dcsc.adp.portal.api.tracking.web.tracking;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ControllerUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.dcsc.adp.portal.api.tracking.Application;
import com.accenture.dcsc.adp.portal.api.tracking.domain.events.tracking.UiTrackingEvent;
import com.accenture.dcsc.adp.portal.api.tracking.infra.SplunkListener;
import com.accenture.dcsc.adp.portal.api.tracking.web.support.Paths;
import com.accenture.dcsc.adp.portal.api.tracking.web.support.PortalLinks;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(Application.BASE_PATH)
public class UiTrackingEventItemController {

    private static final String REQUEST_MAPPING = Paths.UIEVENT_PATH;

    private final SplunkListener splunkListener;

    private final PortalLinks portalLinks;
    
    @Autowired
    public UiTrackingEventItemController(PortalLinks portalLinks, SplunkListener splunkListener) {
        
        Assert.notNull(splunkListener, "'splunkListener' cannot be null.");
        Assert.notNull(portalLinks, "'portalLinks' cannot be null.");
        
        this.splunkListener = splunkListener;
        this.portalLinks = portalLinks;
    }

    @RequestMapping(value = REQUEST_MAPPING, method = RequestMethod.HEAD)
    public ResponseEntity<Object> head() throws JsonProcessingException {

        List<Link> links = new ArrayList<>();
        links.add(portalLinks.linkToSelf());
        links.add(portalLinks.linkToTrackingResource());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LINK, new Links(links).toString());

        return ResponseEntity.noContent().headers(headers).build();
    }

    @RequestMapping(value = REQUEST_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<ResourceSupport> post(@RequestBody final Resource<UiTrackingEvent> resource) throws JsonProcessingException {

        splunkListener.logEvent(
                resource.getContent());

        return ControllerUtils.toResponseEntity(HttpStatus.CREATED, null, null);
    }

}
