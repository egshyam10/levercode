package com.accenture.dcsc.adp.portal.api.tracking.domain.events;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import joptsimple.internal.Strings;
import lombok.Getter;

@Getter
public abstract class DomainEvent {

    private final long timestamp;
    
    public DomainEvent() {
        this.timestamp = System.currentTimeMillis();
    }

    public String getAuthenticatedPrincipalName() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }

        return Strings.EMPTY;
    }

    public abstract EventName getEventName();
}
