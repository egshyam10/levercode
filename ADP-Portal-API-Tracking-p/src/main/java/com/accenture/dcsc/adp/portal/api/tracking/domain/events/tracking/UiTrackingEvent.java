package com.accenture.dcsc.adp.portal.api.tracking.domain.events.tracking;

import java.util.Map;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.accenture.dcsc.adp.portal.api.tracking.domain.events.DomainEvent;
import com.accenture.dcsc.adp.portal.api.tracking.domain.events.EventName;

import joptsimple.internal.Strings;
import lombok.Getter;

@Getter
public class UiTrackingEvent extends DomainEvent {

    // e.g. NavigationButton, Button, 
    private String entity;

    // E.g. Click, Scroll, Login, Edit Project etc
    private String action;

    // e.g. Projects, About, Create adp Project
    private String value;
    
    // e.g. project-dashboard, home, global-navigation, project-service-catalog
    private String category;
    
    // e.g. ID of the current deployment
    private String deploymentId;
    
    // e.g. add arbitrary data to event "project": "name-here"
    private Map<String,Object> data; 
    
    private boolean annoymise = true;

    private UiTrackingEvent() {
        super();
    }

    @Override
    public String getAuthenticatedPrincipalName() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken) && !this.annoymise) {
            return authentication.getName();
        }

        return Strings.EMPTY;
    }
    
    @Override
    public EventName getEventName() {
        return EventName.UI_EVENT;
    }
}
