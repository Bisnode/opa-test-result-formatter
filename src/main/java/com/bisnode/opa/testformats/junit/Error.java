package com.bisnode.opa.testformats.junit;

import com.bisnode.opa.OpaError;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

class Error {
    private final String message;
    private final String type;

    private Error(String message, String type) {
        this.message = message;
        this.type = type;
    }

    @JacksonXmlProperty(isAttribute = true)
    String getMessage() {
        return message;
    }

    @JacksonXmlProperty(isAttribute = true)
    String getType() {
        return type;
    }

    public static Error fromOpaError(OpaError error) {
        return new Error(error.getMessage(), error.getCode());
    }
}