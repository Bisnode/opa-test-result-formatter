package com.bisnode.opa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
class Error {
    private final String code;
    private final String message;
    private final Location location;

    Error(@JsonProperty String code,
          @JsonProperty String message,
          @JsonProperty Location location) {
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
