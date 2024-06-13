package mil.army.usace.hec.dss.api;

import java.time.ZonedDateTime;

public record TimeSeriesRecord(ZonedDateTime startTime, ZonedDateTime endTime, double value, String dataUnits, DssDataType dataType) {
    public static TimeSeriesRecord of(ZonedDateTime startTime, ZonedDateTime endTime, double value, String dataUnits, DssDataType dataType) {
        return new TimeSeriesRecord(startTime, endTime, value, dataUnits, dataType);
    }
}
