package mil.army.usace.hec.dss.internal;

import mil.army.usace.hec.dss.internal.foreign.NativeLibrary;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.logging.Logger;

public final class DssSession implements AutoCloseable {
    private static final Logger logger = Logger.getLogger(DssSession.class.getName());

    private final String dssFilePath;
    private final Arena memorySession;
    private final MemorySegment dssStackPointer;

    /* Static Initializer */
    static {
        NativeLibrary.HEC_DSS.initialize();
    }

    /* Private Constructor */
    private DssSession(String dssFilePath) {
        this.dssFilePath = dssFilePath;
        this.memorySession = Arena.ofConfined();
        this.dssStackPointer = initPointer(dssFilePath);
    }

    /* Factory Methods */
    public static DssSession initiate(String dssFilePath) {
        return new DssSession(dssFilePath);
    }

    /* Public API */
    public Arena getMemorySession() {
        return this.memorySession;
    }

    public MemorySegment getDssStackPointer() {
        return this.dssStackPointer;
    }

    /* Helpers */
    private static MemorySegment initPointer(String dssFilePath) {
        try (Arena memorySession = Arena.ofConfined()) {
            MemorySegment pathHolder = memorySession.allocateFrom(dssFilePath);
            MemorySegment pointerHolder = memorySession.allocate(ValueLayout.ADDRESS);
            // Open DSS File
            int openStatus = hecdss_h.hec_dss_open(pathHolder, pointerHolder);

            if (openStatus == 0) {
                return pointerHolder.get(ValueLayout.ADDRESS, 0);
            } else {
                logger.severe("Failed to open DSS File: " + dssFilePath);
                return null;
            }
        }
    }

    /* AutoClosable */
    @Override
    public void close() {
        // Close DSS file (invalidate 'dssStackPointer')
        int closeStatus = hecdss_h.hec_dss_close(this.dssStackPointer);
        if (closeStatus != 0) {
            logger.severe("Failed to close DSS File: " + this.dssFilePath);
        }
        // Close Memory Session
        this.memorySession.close();
    }
}
