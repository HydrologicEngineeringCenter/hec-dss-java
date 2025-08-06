package mil.army.usace.hec.dss.api.timeseries;

import java.time.LocalDateTime;

public interface TimeSeriesReader {
    TimeSeriesData read();
    TimeSeriesData read(LocalDateTime startTime, LocalDateTime endTime);
}
