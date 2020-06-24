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

    /**
     * @return Opa test error code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Opa test error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return Opa test location.
     */
    public Location getLocation() {
        return location;
    }
}
