package com.bisnode.opa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private final String file;
    private final long row;
    private final long col;

    Location(@JsonProperty("file") String file,
             @JsonProperty("row") long row,
             @JsonProperty("col") long col) {
        this.file = file;
        this.row = row;
        this.col = col;
    }

    /**
     * @return File name containing test case.
     */
    public String getFile() {
        return file;
    }

    /**
     * @return Row with test case.
     */
    public long getRow() {
        return row;
    }

    /**
     * @return Column at which case starts.
     */
    public long getCol() {
        return col;
    }
}
