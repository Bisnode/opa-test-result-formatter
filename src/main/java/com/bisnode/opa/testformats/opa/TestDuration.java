package com.bisnode.opa.testformats.opa;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

class TestDuration implements Summarizable {

    private final long nanos;
    private final DurationUnit durationUnit;

    private TestDuration(long nanos, DurationUnit durationUnit) {
        this.nanos = nanos;
        this.durationUnit = durationUnit;
    }

    static TestDuration ofNanos(long nanos) {
        DurationUnit durationUnit = DurationUnit.ofTimeUnit(biggestTimeUnitFor(nanos));
        return new TestDuration(nanos, durationUnit);
    }

    @Override
    public String summary() {
        return String.format("%s%s", durationUnit.getConverter().convert(nanos), durationUnit.getSuffix());
    }

    private static TimeUnit biggestTimeUnitFor(long nanos) {
        if (nanos > NANOS_IN_MINUTE) {
            return TimeUnit.MINUTES;
        }
        if (nanos > NANOS_IN_SECOND) {
            return TimeUnit.SECONDS;
        }
        if (nanos > NANOS_IN_MILLISECOND) {
            return TimeUnit.MILLISECONDS;
        }
        if (nanos > NANOS_IN_MICROSECOND) {
            return TimeUnit.MICROSECONDS;
        }
        return TimeUnit.NANOSECONDS;
    }

    private static final long NANOS_IN_MINUTE = 60_000_000_000L;
    private static final long NANOS_IN_SECOND = 1_000_000_000L;
    private static final long NANOS_IN_MILLISECOND = 1_000_000L;
    private static final long NANOS_IN_MICROSECOND = 1000L;

    private enum DurationUnit {
        MINUTES(TimeUnit.MINUTES, "min", nanos -> (double) nanos / NANOS_IN_MINUTE),
        SECONDS(TimeUnit.SECONDS, "s", nanos -> (double) nanos / NANOS_IN_SECOND),
        MILLISECONDS(TimeUnit.MILLISECONDS, "ms", nanos -> (double) nanos / NANOS_IN_MILLISECOND),
        MICROSECONDS(TimeUnit.MICROSECONDS, "µs", nanos -> (double) nanos / NANOS_IN_MICROSECOND),
        NANOSECONDS(TimeUnit.NANOSECONDS, "ns", nanos -> nanos);

        private final TimeUnit timeUnit;
        private final String suffix;
        private final FromNanosConverter converter;

        DurationUnit(TimeUnit timeUnit, String suffix, FromNanosConverter converter) {
            this.timeUnit = timeUnit;
            this.suffix = suffix;
            this.converter = converter;
        }

        String getSuffix() {
            return suffix;
        }

        FromNanosConverter getConverter() {
            return converter;
        }

        static DurationUnit ofTimeUnit(TimeUnit timeUnit) {
            return Arrays.stream(values())
                    .filter(durationUnit -> durationUnit.timeUnit.equals(timeUnit))
                    .findAny()
                    // if it's not here it has to be bigger than minutes
                    // so we retrun the biggest one
                    .orElse(MINUTES);
        }

    }

    private interface FromNanosConverter {
        double convert(long nanos);
    }

}
