package com.bisnode.opa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpaTestCase {
    private final Location location;
    private final String pkg;
    private final String name;
    private final long duration;
    @Nullable
    private final Boolean fail;
    @Nullable
    private final OpaError error;


    OpaTestCase(@JsonProperty("location") Location location,
                       @JsonProperty("package") String pkg,
                       @JsonProperty("name") String name,
                       @JsonProperty("duration") long duration,
                       @JsonProperty("fail") @Nullable Boolean fail,
                       @JsonProperty("error") @Nullable OpaError error) {
        this.location = location;
        this.pkg = pkg;
        this.name = name;
        this.duration = duration;
        this.fail = fail;
        this.error = error;
    }

    /**
     * @return Test location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return Test package.
     */
    public String getPackage() {
        return pkg;
    }

    /**
     * @return Test name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Test duration in nanoseconds.
     */
    public long getDuration() {
        return duration;
    }

    /**
     * @return {@code Boolean.TRUE} if the test failed, {@code null} otherwise.
     */
    @Nullable
    public Boolean getFail() {
        return fail;
    }

    /**
     * @return {@link OpaError} instance if test resulted in errors, {@code null} otherwise.
     */
    @Nullable
    public OpaError getError() {
        return error;
    }
}
