package mil.army.usace.hec.dss.internal;

import mil.army.usace.hec.dss.api.timeseries.TimeSeriesReader;

public interface HecDss extends AutoCloseable {
    HecDss open(String dssFilePath);
    TimeSeriesReader getTimeSeriesReader();
}
