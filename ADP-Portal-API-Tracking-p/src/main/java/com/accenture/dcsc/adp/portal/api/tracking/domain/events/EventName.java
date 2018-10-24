package com.accenture.dcsc.adp.portal.api.tracking.domain.events;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EventName {

    UI_EVENT("UiTrackingEvent");

    private final String value;

    private EventName(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
