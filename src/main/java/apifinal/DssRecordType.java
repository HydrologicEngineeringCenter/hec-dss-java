package apifinal;

import java.util.Set;

/**
 * Enumeration of DSS data types.
 */
public enum DssRecordType {
    UNKNOWN,
    REGULAR_TIME_SERIES_PROFILE,
    REGULAR_TIME_SERIES,
    IRREGULAR_TIME_SERIES,
    PAIRED_DATA,
    TEXT,
    GRIDDED_DATA,
    TIN,
    LOCATION_INFO,
    ARRAY_CONTAINER;

    private static final Set<DssRecordType> supportedTypes = Set.of(
            IRREGULAR_TIME_SERIES, REGULAR_TIME_SERIES, PAIRED_DATA, GRIDDED_DATA, ARRAY_CONTAINER
    );

    /**
     * Returns DssRecordType Enumeration from integer value.
     */
    public static DssRecordType fromInt(int recType) {
        if (90 <= recType && recType <= 93) {
            return ARRAY_CONTAINER;
        } else if (100 <= recType && recType < 110) {
            return (recType == 102 || recType == 107) ?
                    REGULAR_TIME_SERIES_PROFILE : REGULAR_TIME_SERIES;
        } else if (110 <= recType && recType < 200) {
            return IRREGULAR_TIME_SERIES;
        } else if (200 <= recType && recType < 300) {
            return PAIRED_DATA;
        } else if (300 <= recType && recType < 400) {
            return TEXT;
        } else if (400 <= recType && recType < 450) {
            return GRIDDED_DATA;
        } else if (recType == 450) {
            return TIN;
        } else if (recType == 20) {
            return LOCATION_INFO;
        }
        return UNKNOWN;
    }

    public boolean isSupported() {
        return supportedTypes.contains(this);
    }
}
