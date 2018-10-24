package com.accenture.dcsc.adp.portal.api.tracking.web.discovery;

import java.util.Collections;

import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.accenture.dcsc.adp.portal.api.tracking.web.support.PortalLinks;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@BasePathAwareController
public class DiscoveryController {

    private final PortalLinks portalLinks;

    public DiscoveryController(PortalLinks portalLinks) {
        this.portalLinks = portalLinks;
    }

    @RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
    public ResponseEntity<Object> getDiscovery() throws JsonProcessingException {

        RootResource root = new RootResource("adp Management Portal API Tracking", "Welcome to the adp Management Portal Tracking API");

        root.add(portalLinks.linkToSelf());
        root.add(portalLinks.linkToTrackingResource());
        return ResponseEntity.ok(root);
    }

    @RequestMapping(value = { "/", "" }, method = RequestMethod.OPTIONS)
    public ResponseEntity<Object> optionsDiscovery() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAllow(Collections.singleton(HttpMethod.GET));

        return ResponseEntity.ok().headers(headers).build();
    }

    @RequestMapping(value = { "/", "" }, method = RequestMethod.HEAD)
    public ResponseEntity<Object> headDiscovery() {
        return ResponseEntity.noContent().build();
    }

    @Getter
    @EqualsAndHashCode(callSuper = false)
    private static class RootResource extends RepositoryLinksResource {
        private final String name;
        private final String description;

        public RootResource(String name, String description) {
            super();
            this.name = name;
            this.description = description;
        }
    }

}
