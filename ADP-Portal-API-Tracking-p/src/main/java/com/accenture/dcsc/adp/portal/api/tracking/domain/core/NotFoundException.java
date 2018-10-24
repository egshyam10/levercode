package com.accenture.dcsc.adp.portal.api.tracking.domain.core;

@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}