package mil.army.usace.hec.dss.internal;

import mil.army.usace.hec.dss.TestUtil;
import mil.army.usace.hec.dss.api.TimeSeriesData;
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
        TimeSeriesData timeSeriesData = DssTimeSeriesReader.getTimeSeries(dssFileName, dssPathname, startTime, endTime);
        assertEquals(77, timeSeriesData.timeSeriesRecords().size());
    }
}