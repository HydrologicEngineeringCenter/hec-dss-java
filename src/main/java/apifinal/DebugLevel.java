package apifinal;

/**
 * Debug levels for DSS operations.
 */
public enum DebugLevel {
    NONE(0),
    LOW(3),
    MEDIUM(7),
    HIGH(11),
    MAXIMUM(15);

    private final int value;

    DebugLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
