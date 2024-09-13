package mil.army.usace.hec.dss.internal;

import mil.army.usace.hec.dss.internal.foreign.ForeignLanguage;
import mil.army.usace.hec.dss.internal.foreign.memory.allocator.MemoryAllocator;
import mil.army.usace.hec.dss.internal.foreign.memory.parser.MemoryParser;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public final class DssGridReader {
    private static final int RANGE_TABLES_LENGTH = 0;

    private static final int DATA_UNITS_BUFFER_LENGTH = 100;
    private static final int DATA_SOURCE_BUFFER_LENGTH = 100;
    private static final int SRS_NAME_BUFFER_LENGTH = 100;
    private static final int SRS_DEFINITION_BUFFER_LENGTH = 100;
    private static final int TIME_ZONE_ID_BUFFER_LENGTH = 100;

    private DssGridReader() {
        // Utility Class
    }

    public static RawDssGrid getGriddedData(String dssFileName, String dssPathname) {
        RawDssGrid noDataGrid = retrieveRawDssGrid(dssFileName, dssPathname, 0);
        int dataLength = noDataGrid.numberOfCellsX() * noDataGrid.numberOfCellsY();
        return retrieveRawDssGrid(dssFileName, dssPathname, dataLength);
    }

    private static RawDssGrid retrieveRawDssGrid(String dssFileName, String dssPathname, int dataToReadCount) {
        try (DssSession dssSession = DssSession.initiate(dssFileName)) {
            Arena memorySession = dssSession.getMemorySession();
            MemoryAllocator memoryAllocator = MemoryAllocator.create(ForeignLanguage.C, memorySession);

            MemorySegment dssPointerInput = dssSession.getDssStackPointer();
            MemorySegment dssPathnameInput = memoryAllocator.allocateFromString(dssPathname);

            MemorySegment typeOutput = memoryAllocator.allocateInts(1);
            MemorySegment dataTypeOutput = memoryAllocator.allocateInts(1);
            MemorySegment lowerLeftCellXOutput = memoryAllocator.allocateInts(1);
            MemorySegment lowerLeftCellYOutput = memoryAllocator.allocateInts(1);
            MemorySegment numberOfCellsXOutput = memoryAllocator.allocateInts(1);
            MemorySegment numberOfCellsYOutput = memoryAllocator.allocateInts(1);
            MemorySegment numberOfRangesOutput = memoryAllocator.allocateInts(1);
            MemorySegment srsDefinitionTypeOutput = memoryAllocator.allocateInts(1);
            MemorySegment timeZoneRawOffsetOutput = memoryAllocator.allocateInts(1);
            MemorySegment isIntervalOutput = memoryAllocator.allocateInts(1);
            MemorySegment isTimeStampedOutput = memoryAllocator.allocateInts(1);
            MemorySegment dataUnitsOutput = memoryAllocator.allocateChars(DATA_UNITS_BUFFER_LENGTH);
            MemorySegment dataSourceOutput = memoryAllocator.allocateChars(DATA_SOURCE_BUFFER_LENGTH);
            MemorySegment srsNameOutput = memoryAllocator.allocateChars(SRS_NAME_BUFFER_LENGTH);
            MemorySegment srsDefinitionOutput = memoryAllocator.allocateChars(SRS_DEFINITION_BUFFER_LENGTH);
            MemorySegment timeZoneIdOutput = memoryAllocator.allocateChars(TIME_ZONE_ID_BUFFER_LENGTH);
            MemorySegment cellSizeOutput = memoryAllocator.allocateFloats(1);
            MemorySegment xCoordOfGridCellZeroOutput = memoryAllocator.allocateFloats(1);
            MemorySegment yCoordOfGridCellZeroOutput = memoryAllocator.allocateFloats(1);
            MemorySegment nullValueOutput = memoryAllocator.allocateFloats(1);
            MemorySegment maxDataValueOutput = memoryAllocator.allocateFloats(1);
            MemorySegment minDataValueOutput = memoryAllocator.allocateFloats(1);
            MemorySegment meanDataValueOutput = memoryAllocator.allocateFloats(1);
            MemorySegment rangeLimitTableOutput = memoryAllocator.allocateFloats(1);
            MemorySegment numberEqualOrExceedingRangeLimitOutput = memoryAllocator.allocateFloats(1);
            MemorySegment dataOutput = memoryAllocator.allocateFloats(dataToReadCount);

            int status = hecdss_h.hec_dss_gridRetrieve(
                    dssPointerInput,
                    dssPathnameInput,
                    dataToReadCount == 0 ? 0 : 1,
                    typeOutput,
                    dataTypeOutput,
                    lowerLeftCellXOutput,
                    lowerLeftCellYOutput,
                    numberOfCellsXOutput,
                    numberOfCellsYOutput,
                    numberOfRangesOutput,
                    srsDefinitionTypeOutput,
                    timeZoneRawOffsetOutput,
                    isIntervalOutput,
                    isTimeStampedOutput,
                    dataUnitsOutput, DATA_UNITS_BUFFER_LENGTH,
                    dataSourceOutput, DATA_SOURCE_BUFFER_LENGTH,
                    srsNameOutput, SRS_NAME_BUFFER_LENGTH,
                    srsDefinitionOutput, SRS_DEFINITION_BUFFER_LENGTH,
                    timeZoneIdOutput, TIME_ZONE_ID_BUFFER_LENGTH,
                    cellSizeOutput,
                    xCoordOfGridCellZeroOutput,
                    yCoordOfGridCellZeroOutput,
                    nullValueOutput,
                    maxDataValueOutput,
                    minDataValueOutput,
                    meanDataValueOutput,
                    rangeLimitTableOutput, RANGE_TABLES_LENGTH,
                    numberEqualOrExceedingRangeLimitOutput,
                    dataOutput, dataToReadCount
            );

            return new RawDssGrid(
                    MemoryParser.parseInt(typeOutput),
                    MemoryParser.parseInt(dataTypeOutput),
                    MemoryParser.parseInt(lowerLeftCellXOutput),
                    MemoryParser.parseInt(lowerLeftCellYOutput),
                    MemoryParser.parseInt(numberOfCellsXOutput),
                    MemoryParser.parseInt(numberOfCellsYOutput),
                    MemoryParser.parseInt(numberOfRangesOutput),
                    MemoryParser.parseInt(srsDefinitionTypeOutput),
                    MemoryParser.parseInt(timeZoneRawOffsetOutput),
                    MemoryParser.parseInt(isIntervalOutput),
                    MemoryParser.parseInt(isTimeStampedOutput),
                    MemoryParser.parseString(dataUnitsOutput),
                    MemoryParser.parseString(dataSourceOutput),
                    MemoryParser.parseString(srsNameOutput),
                    MemoryParser.parseString(srsDefinitionOutput),
                    MemoryParser.parseString(timeZoneIdOutput),
                    MemoryParser.parseFloat(cellSizeOutput),
                    MemoryParser.parseFloat(xCoordOfGridCellZeroOutput),
                    MemoryParser.parseFloat(yCoordOfGridCellZeroOutput),
                    MemoryParser.parseFloat(nullValueOutput),
                    MemoryParser.parseFloat(maxDataValueOutput),
                    MemoryParser.parseFloat(minDataValueOutput),
                    MemoryParser.parseFloat(meanDataValueOutput),
                    MemoryParser.parseFloat(rangeLimitTableOutput),
                    MemoryParser.parseFloat(numberEqualOrExceedingRangeLimitOutput),
                    MemoryParser.parseFloats(dataOutput)
            );
        }
    }
}
