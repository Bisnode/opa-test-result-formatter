package com.bisnode.opa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
class OpaTestCase {
    private final Location location;
    private final String pkg;
    private final String name;
    private final long duration;
    @Nullable
    private final Boolean fail;
    @Nullable
    private final Error error;

    OpaTestCase(@JsonProperty Location location,
                @JsonProperty("package") String pkg,
                @JsonProperty String name,
                @JsonProperty long duration,
                @JsonProperty @Nullable Boolean fail,
                @JsonProperty @Nullable Error error) {
        this.location = location;
        this.pkg = pkg;
        this.name = name;
        this.duration = duration;
        this.fail = fail;
        this.error = error;
    }

    public Location getLocation() {
        return location;
    }

    public String getPackage() {
        return pkg;
    }

    public String getName() {
        return name;
    }

    public long getDuration() {
        return duration;
    }

    @Nullable
    public Boolean getFail() {
        return fail;
    }

    @Nullable
    public Error getError() {
        return error;
    }
}
