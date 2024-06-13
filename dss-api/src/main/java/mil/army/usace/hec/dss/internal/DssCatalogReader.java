package mil.army.usace.hec.dss.internal;

import mil.army.usace.hec.dss.internal.foreign.ForeignLanguage;
import mil.army.usace.hec.dss.internal.foreign.memory.allocator.MemoryAllocator;
import mil.army.usace.hec.dss.internal.foreign.memory.parser.MemoryParser;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public final class DssCatalogReader {
    private static final Logger logger = Logger.getLogger(DssCatalogReader.class.getName());
    private static final int MAX_PATHNAME_LENGTH = 1000;

    private DssCatalogReader() {
        // Utility Class
    }

    public static List<String> getCatalog(String dssFileName, String dssPathname) {
        try (DssSession dssSession = DssSession.initiate(dssFileName)) {
            Arena memorySession = dssSession.getMemorySession();
            MemoryAllocator memoryAllocator = MemoryAllocator.create(ForeignLanguage.C, memorySession);

            int recordCount = hecdss_h.hec_dss_record_count(dssSession.getDssStackPointer());
            int charBufferLength = MAX_PATHNAME_LENGTH * recordCount;

            MemorySegment pathBuffer = memoryAllocator.allocateChars(charBufferLength);
            MemorySegment recordTypes = memoryAllocator.allocateInts(recordCount);
            MemorySegment pathFilter = memoryAllocator.allocateFromString(dssPathname);

            hecdss_h.hec_dss_catalog(dssSession.getDssStackPointer(), pathBuffer, recordTypes, pathFilter, recordCount, MAX_PATHNAME_LENGTH);
            return MemoryParser.parseStrings(ForeignLanguage.C, pathBuffer);
        } catch (Exception exception) {
            logger.warning("Failed to get catalog for: " + dssFileName + " | " + dssPathname);
            logger.warning(exception.toString());
            return Collections.emptyList();
        }
    }
}
