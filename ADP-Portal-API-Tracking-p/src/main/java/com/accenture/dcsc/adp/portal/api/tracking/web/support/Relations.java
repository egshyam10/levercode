package com.accenture.dcsc.adp.portal.api.tracking.web.support;

import com.accenture.dcsc.adp.portal.api.tracking.Application;

public final class Relations {

    public static final String TRACKING_REL = "tracking";

    private static final String NS = Application.CURIE_NAMESPACE + ":";

    public static final String NS_UIEVENT_REL = NS + TRACKING_REL;


    private Relations() {
        throw new UnsupportedOperationException();
    }
}
