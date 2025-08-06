package mil.army.usace.hec.dss.api.timeseries;

import mil.army.usace.hec.dss.internal.RawDssTimeSeries;

import java.time.LocalDateTime;
import java.util.HashMap;

public interface TimeSeriesData {
    TimeSeriesMetadata metadata();

    double at(LocalDateTime dateTime);

    static TimeSeriesData of(RawDssTimeSeries timeSeries) {
        return TimeSeriesDataImpl.of(timeSeries);
    }

    static TimeSeriesData empty() {
        return new TimeSeriesDataImpl(TimeSeriesMetadata.empty(), new HashMap<>());
    }
}
