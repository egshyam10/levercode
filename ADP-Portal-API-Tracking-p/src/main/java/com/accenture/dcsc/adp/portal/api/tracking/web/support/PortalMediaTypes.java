package com.accenture.dcsc.adp.portal.api.tracking.web.support;

import org.springframework.http.MediaType;

public final class PortalMediaTypes {

    public static final String HAL_JSON_VALUE = "application/hal+json";
    public static final String JSON_VALUE = MediaType.APPLICATION_JSON_VALUE;
    public static final String FORM_URLENCODED_VALUE = MediaType.APPLICATION_FORM_URLENCODED_VALUE;

    private PortalMediaTypes() {
        throw new UnsupportedOperationException();
    }
}
