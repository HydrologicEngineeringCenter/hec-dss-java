package mil.army.usace.hec.dss.api.timeseries;

import mil.army.usace.hec.dss.api.DssDataType;

import java.time.ZoneOffset;

record TimeSeriesMetadataImpl(
        String unit,
        double noDataValue,
        DssDataType recordingIntervalType,
        ZoneOffset zoneOffset
) implements TimeSeriesMetadata {

}
