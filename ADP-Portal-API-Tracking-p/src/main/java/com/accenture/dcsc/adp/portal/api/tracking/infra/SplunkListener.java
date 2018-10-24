package com.accenture.dcsc.adp.portal.api.tracking.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.event.AuthenticationCredentialsNotFoundEvent;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureExpiredEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "splunk.logger")
@Component
public class SplunkListener {

    private ObjectMapper mapper;

    @Autowired
    public SplunkListener(ObjectMapper mapper) {
        this.mapper = mapper;
        mapper.setSerializationInclusion(Include.NON_NULL);
    }
    
    @EventListener(classes = { //
            AuthenticationFailureBadCredentialsEvent.class, //
            AuthenticationCredentialsNotFoundEvent.class, //
            AuthenticationFailureExpiredEvent.class })
    public void logEvent(Object event) throws JsonProcessingException {
        log.info("{}", mapper.writeValueAsString(event));
    }

    @EventListener
    private void logEvent(AuthenticationSuccessEvent event) throws JsonProcessingException {
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(event.getAuthentication().getPrincipal(), "N/A",
                event.getAuthentication().getAuthorities());
        AuthenticationSuccessEvent success = new AuthenticationSuccessEvent(user);
        log.info("{}", mapper.writeValueAsString(success));
    }
}
