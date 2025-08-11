package mil.army.usace.hec.dss.internal;

import mil.army.usace.hec.dss.TestUtil;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DssTimeSeriesReaderTest {
    @Test
    void RetrieveRegularTimeSeries() {
        String dssFileName = TestUtil.getResourceFile("examples-all-data-types.dss").toString();
        String dssPathname = "/regular-time-series/GAPT/FLOW/*/6Hour/forecast1/";
        ZonedDateTime startTime = ZonedDateTime.parse("2021-09-15T07:00:00Z");
        ZonedDateTime endTime = ZonedDateTime.parse("2021-10-04T07:00:00Z");
        DssTimeSeriesImpl timeSeriesData = DssTimeSeriesReader.getTimeSeries(dssFileName, dssPathname, startTime, endTime);
        assertEquals(77, timeSeriesData.times().length);
    }

    @Test
    void RetrieveIrregularTimeSeries() {
        String dssFileName = TestUtil.getResourceFile("examples-all-data-types.dss").toString();
        String dssPathname = "/irregular-time-series/FAIR OAKS CA/FLOW-ANNUAL PEAK/01Jan1900/IR-Century/USGS/";
        ZonedDateTime startTime = ZonedDateTime.parse("1905-03-20T00:00:00Z");
        ZonedDateTime endTime = ZonedDateTime.parse("2017-02-11T00:00:00Z");
        DssTimeSeriesImpl timeSeriesData = DssTimeSeriesReader.getTimeSeries(dssFileName, dssPathname, startTime, endTime);
        assertEquals(112, timeSeriesData.times().length);
    }
}