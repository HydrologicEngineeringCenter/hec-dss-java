package mil.army.usace.hec.dss.internal;

import apifinal.DssTimeSeries;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

public record DssTimeSeriesImpl(
        Instant[] times,
        double[] values,
        String dataUnits,
        String dataType
) implements DssTimeSeries {
    static DssTimeSeriesImpl empty() {
        return new DssTimeSeriesImpl(new Instant[0], new double[0], "", "");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DssTimeSeriesImpl(
                Instant[] times1, double[] values1, String units, String type
        ))) return false;
        return Objects.deepEquals(times, times1)
                && Objects.deepEquals(values, values1)
                && Objects.equals(dataType, type)
                && Objects.equals(dataUnits, units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(times), Arrays.hashCode(values), dataUnits, dataType);
    }

    @Override
    public String toString() {
        return "DssTimeSeries{" +
                "times=" + Arrays.toString(times) +
                ", values=" + Arrays.toString(values) +
                ", dataUnits='" + dataUnits + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }
}
