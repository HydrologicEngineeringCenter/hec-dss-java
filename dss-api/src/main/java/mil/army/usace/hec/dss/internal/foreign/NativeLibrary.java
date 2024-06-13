package mil.army.usace.hec.dss.internal.foreign;

import java.util.logging.Logger;

public enum NativeLibrary {
    HEC_DSS("hecdss");

    private static final Logger logger = Logger.getLogger(NativeLibrary.class.getName());
    private final String libraryName;

    NativeLibrary(String libraryName) {
        this.libraryName = libraryName;
    }

    public void initialize() {
        try {
            System.loadLibrary(this.libraryName);
        } catch (Exception exception) {
            logger.severe("Failed to load library: " + this.libraryName);
        }
    }
}
