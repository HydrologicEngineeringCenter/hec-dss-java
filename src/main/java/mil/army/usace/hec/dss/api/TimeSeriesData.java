package mil.army.usace.hec.dss.api;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public record TimeSeriesData(List<TimeSeriesRecord> timeSeriesRecords) {
    private static final Logger logger = Logger.getLogger(TimeSeriesData.class.getName());
    private static final LocalDateTime BASE_JULIAN_TIME = LocalDateTime.of(1900,1,1,0,0).minusDays(1);
    private static final ZoneId BASE_ZONE_ID = ZoneId.of("Z");

    public TimeSeriesData(List<TimeSeriesRecord> timeSeriesRecords) {
        this.timeSeriesRecords = Collections.unmodifiableList(timeSeriesRecords);
    }

    public static TimeSeriesData create(int[] timeArray, double[] valueArray, int timeGranularitySeconds, String dataUnits, String dataType) {
        if (timeArray.length != valueArray.length) {
            logger.severe("Mismatching sizes between timeArray and valueArray");
            return emptyTimeSeries();
        }

        List<TimeSeriesRecord> timeSeriesRecords = new ArrayList<>();
        ChronoUnit timeDeltaUnit = toChronoUnit(timeGranularitySeconds);
        DssDataType dssDataType = DssDataType.fromDssDataTypeString(dataType);
        for (int i = 0; i < timeArray.length; i++) {
            int timeDeltaCount = timeArray[i];
            ZonedDateTime time = toZonedDateTime(timeDeltaCount, timeDeltaUnit);
            double value = valueArray[i];

            TimeSeriesRecord timeSeriesRecord = TimeSeriesRecord.of(time, time, value, dataUnits, dssDataType);
            timeSeriesRecords.add(timeSeriesRecord);
        }

        return new TimeSeriesData(timeSeriesRecords);
    }

    public static TimeSeriesData emptyTimeSeries() {
        return new TimeSeriesData(Collections.emptyList());
    }

    /* Helpers */
    private static ZonedDateTime toZonedDateTime(long timeDeltaCount, ChronoUnit timeDeltaUnit) {
        return BASE_JULIAN_TIME.plus(timeDeltaCount, timeDeltaUnit).atZone(BASE_ZONE_ID);
    }

    private static ChronoUnit toChronoUnit(int timeGranularitySeconds) {
        if (timeGranularitySeconds == 60) {
            return ChronoUnit.MINUTES;
        } else if (timeGranularitySeconds == 3600) {
            return ChronoUnit.HOURS;
        } else if (timeGranularitySeconds == 86400) {
            return ChronoUnit.DAYS;
        } else {
            logger.severe("Unsupported time granularity seconds: " + timeGranularitySeconds);
            return ChronoUnit.SECONDS;
        }
    }
}
