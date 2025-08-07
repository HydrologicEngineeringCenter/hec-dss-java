package mil.army.usace.hec.dss.internal;

import java.util.Arrays;
import java.util.Objects;

public record RawDssTimeSeries(
        int[] times,
        double[] values,
        int timeGranularitySeconds,
        String dataUnits,
        String dataType
) {
    static RawDssTimeSeries empty() {
        return new RawDssTimeSeries(new int[0], new double[0], 0, "", "");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RawDssTimeSeries(
                int[] times1, double[] values1, int granularitySeconds, String units, String type
        ))) return false;
        return timeGranularitySeconds == granularitySeconds
                && Objects.deepEquals(times, times1)
                && Objects.deepEquals(values, values1)
                && Objects.equals(dataType, type)
                && Objects.equals(dataUnits, units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(times), Arrays.hashCode(values), timeGranularitySeconds, dataUnits, dataType);
    }

    @Override
    public String toString() {
        return "RawDssTimeSeries{" +
                "times=" + Arrays.toString(times) +
                ", values=" + Arrays.toString(values) +
                ", timeGranularitySeconds=" + timeGranularitySeconds +
                ", dataUnits='" + dataUnits + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }
}
