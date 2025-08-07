package mil.army.usace.hec.dss.internal;

import mil.army.usace.hec.dss.api.timeseries.TimeSeriesReader;

import java.util.stream.Stream;

public interface HecDss extends AutoCloseable {
    HecDss open(String dssFilePath);
    TimeSeriesReader getTimeSeriesReader();
    Stream<String> getCatalog();
}
