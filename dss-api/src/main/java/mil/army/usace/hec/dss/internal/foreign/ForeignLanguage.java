package mil.army.usace.hec.dss.internal.foreign;

import java.lang.foreign.ValueLayout;

public enum ForeignLanguage {
    C;

    /* Language's Memory ValueLayout */
    public ValueLayout getCharLayout() {
        return switch (this) {
            case C -> ValueLayout.JAVA_BYTE;
        };
    }

    public ValueLayout getIntLayout() {
        return switch (this) {
            case C -> ValueLayout.JAVA_INT;
        };
    }

    public ValueLayout getDoubleLayout() {
        return switch (this) {
            case C -> ValueLayout.JAVA_DOUBLE;
        };
    }
}
