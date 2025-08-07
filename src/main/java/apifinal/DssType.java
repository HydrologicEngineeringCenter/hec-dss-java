package apifinal;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enumeration representing different types of DSS data.
 */
public enum DssType {
    PER_AVER("PER-AVER"),
    PER_CUM("PER-CUM"),
    INST_VAL("INST-VAL"),
    INST_CUM("INST-CUM"),
    FREQ("FREQ"),       // Not a DSS standard yet
    PER_MAX("PER-MAX"), // Not a DSS standard yet
    PER_MIN("PER-MIN"), // Not a DSS standard yet
    CONST("CONST");     // Not a DSS standard yet

    private final String value;

    DssType(String value) {
        this.value = value;
    }

    public static Optional<DssType> fromString(String string) {
        return Arrays.stream(DssType.values()).filter(t -> t.value.equalsIgnoreCase(string)).findAny();
    }
}
