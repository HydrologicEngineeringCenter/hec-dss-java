package mil.army.usace.hec.dss.api.timeseries;

import mil.army.usace.hec.dss.api.DssDataType;

import java.time.ZoneOffset;

public interface TimeSeriesMetadata {
    String unit();

    double noDataValue();

    DssDataType recordingIntervalType();

    ZoneOffset zoneOffset();

    static TimeSeriesMetadata empty() {
        return new TimeSeriesMetadataImpl("", Double.NaN, DssDataType.UNKNOWN, ZoneOffset.UTC);
    }
}