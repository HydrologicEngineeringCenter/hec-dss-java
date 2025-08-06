package mil.army.usace.hec.dss.api;

// Maybe rename to: RecordingIntervalType
public enum DssDataType {
    INSTANTANEOUS, ACCUMULATION, AVERAGE, UNKNOWN;

    public static DssDataType fromDssDataTypeString(String dssDataTypeString) {
        return switch (dssDataTypeString.toUpperCase()) {
            case "INST-VAL" -> INSTANTANEOUS;
            case "PER-CUM" -> ACCUMULATION;
            case "PER-AVER" -> AVERAGE;
            default -> UNKNOWN;
        };
    }
}
