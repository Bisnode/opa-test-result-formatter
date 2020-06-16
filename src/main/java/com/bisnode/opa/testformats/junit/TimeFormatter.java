package com.bisnode.opa.testformats.junit;

import java.util.Locale;

class TimeFormatter {
    private TimeFormatter() {
    }

    static String nanosToSeconds(long nanos) {
        return String.format(Locale.US, "%.3f", nanos / 1_000_000_000.0);
    }
}
