package mil.army.usace.hec.dss.api.timeseries;

import mil.army.usace.hec.dss.internal.RawDssTimeSeries;

import java.time.LocalDateTime;
import java.util.Map;

record TimeSeriesDataImpl(
        TimeSeriesMetadata metadata,
        Map<LocalDateTime, Double> data
) implements TimeSeriesData {

    static TimeSeriesData of(RawDssTimeSeries timeSeries) {

        return null;
    }

    @Override
    public double at(LocalDateTime dateTime) {
        return data.getOrDefault(dateTime, metadata().noDataValue());
    }

}
