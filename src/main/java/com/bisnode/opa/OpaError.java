package com.bisnode.opa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpaError {
    private final String code;
    private final String message;
    private final Location location;

    OpaError(@JsonProperty("code") String code,
             @JsonProperty("message") String message,
             @JsonProperty("location") Location location) {
        this.code = code;
        this.message = message;
        this.location = location;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Location getLocation() {
        return location;
    }
}
