package mil.army.usace.hec.dss.internal;

import mil.army.usace.hec.dss.api.TimeSeriesData;
import mil.army.usace.hec.dss.internal.foreign.ForeignLanguage;
import mil.army.usace.hec.dss.internal.foreign.memory.allocator.MemoryAllocator;
import mil.army.usace.hec.dss.internal.foreign.memory.parser.MemoryParser;
import mil.army.usace.hec.dss.internal.util.PrimitiveArrayUtils;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.time.ZonedDateTime;
import java.util.logging.Logger;

public class DssTimeSeriesReader {
    private static final Logger logger = Logger.getLogger(DssTimeSeriesReader.class.getName());
    private static final int UNITS_BUFFER_LENGTH = 100;
    private static final int DATA_TYPE_BUFFER_LENGTH = 100;

    private DssTimeSeriesReader() {
        // Utility Class
    }

    public static TimeSeriesData getTimeSeries(String dssFileName, String dssPathname, ZonedDateTime startTime, ZonedDateTime endTime) {
        String startDateString = startTime.toLocalDate().toString();
        String startTimeString = startTime.toLocalTime().toString();
        String endDateString = endTime.toLocalDate().toString();
        String endTimeString = endTime.toLocalTime().toString();
        return getTimeSeries(dssFileName, dssPathname, startDateString, startTimeString, endDateString, endTimeString);
    }
    
    private static TimeSeriesData getTimeSeries(String dssFileName, String dssPathname, String startDate, String startTime, String endDate, String endTime) {
        try (DssSession dssSession = DssSession.initiate(dssFileName)) {
            Arena memorySession = dssSession.getMemorySession();
            MemoryAllocator memoryAllocator = MemoryAllocator.create(ForeignLanguage.C, memorySession);

            int[] numberValuesAndQualityWidth = getTimeSeriesSizes(dssFileName, dssPathname, startDate, startTime, endDate, endTime);
            int numberValues = numberValuesAndQualityWidth[0];
            int qualityWidth = numberValuesAndQualityWidth[1];
            int unitsBufferLength = UNITS_BUFFER_LENGTH;
            int dataTypeBufferLength = DATA_TYPE_BUFFER_LENGTH;

            MemorySegment dssPointerInput = dssSession.getDssStackPointer();
            MemorySegment dssPathnameInput = memoryAllocator.allocateFromString(dssPathname);
            MemorySegment startDateInput = memoryAllocator.allocateFromString(startDate);
            MemorySegment startTimeInput = memoryAllocator.allocateFromString(startTime);
            MemorySegment endDateInput = memoryAllocator.allocateFromString(endDate);
            MemorySegment endTimeInput = memoryAllocator.allocateFromString(endTime);

            MemorySegment timeArrayOutput = memoryAllocator.allocateInts(numberValues);
            MemorySegment valueArrayOutput = memoryAllocator.allocateDoubles(numberValues);
            MemorySegment numberValuesReadOutput = memoryAllocator.allocateInts(numberValues);
            MemorySegment qualityOutput = memoryAllocator.allocateInts(numberValues);
            MemorySegment julianBaseDateOutput = memoryAllocator.allocateInts(numberValues);
            MemorySegment timeGranularitySecondsOutput = memoryAllocator.allocateInts(numberValues);
            MemorySegment dataUnitsOutput = memoryAllocator.allocateChars(unitsBufferLength);
            MemorySegment dataTypeOutput = memoryAllocator.allocateChars(dataTypeBufferLength);

            int status = hecdss_h.hec_dss_tsRetrieve(
                    dssPointerInput,
                    dssPathnameInput,
                    startDateInput,
                    startTimeInput,
                    endDateInput,
                    endTimeInput,
                    timeArrayOutput,
                    valueArrayOutput,
                    numberValues,
                    numberValuesReadOutput,
                    qualityOutput,
                    qualityWidth,
                    julianBaseDateOutput,
                    timeGranularitySecondsOutput,
                    dataUnitsOutput,
                    unitsBufferLength,
                    dataTypeOutput,
                    dataTypeBufferLength
            );

            if (status == 0) {
                int[] timeArray = MemoryParser.parseInts(timeArrayOutput);
                double[] valueArray = MemoryParser.parseDoubles(valueArrayOutput);
                int numberValuesRead = MemoryParser.parseInt(numberValuesReadOutput);

                timeArray = PrimitiveArrayUtils.trimArray(timeArray, numberValuesRead);
                valueArray = PrimitiveArrayUtils.trimArray(valueArray, numberValuesRead);

                int timeGranularitySeconds = MemoryParser.parseInt(timeGranularitySecondsOutput);
                String dataUnits = MemoryParser.parseString(dataUnitsOutput);
                String dataType = MemoryParser.parseString(dataTypeOutput);

                boolean isValid = validateOutputs(timeArray, valueArray, numberValuesRead);
                if (!isValid) {
                    return TimeSeriesData.emptyTimeSeries();
                }

                return TimeSeriesData.create(timeArray, valueArray, timeGranularitySeconds, dataUnits, dataType);
            } else {
                String message = formatLogMessage("Failed tsRetrieve", dssFileName, dssPathname, startDate, startTime, endDate, endTime);
                logger.warning(message);
            }
        } catch (Exception exception) {
            logger.severe(exception.getMessage());
        }

        return TimeSeriesData.emptyTimeSeries();
    }

    private static int[] getTimeSeriesSizes(String dssFileName, String dssPathname, String startDate, String startTime, String endDate, String endTime) {
        try (DssSession dssSession = DssSession.initiate(dssFileName)) {
            Arena memorySession = dssSession.getMemorySession();
            MemoryAllocator memoryAllocator = MemoryAllocator.create(ForeignLanguage.C, memorySession);

            MemorySegment dssPointer = dssSession.getDssStackPointer();
            MemorySegment dssPathnameInput = memoryAllocator.allocateFromString(dssPathname);
            MemorySegment startDateInput = memoryAllocator.allocateFromString(startDate);
            MemorySegment startTimeInput = memoryAllocator.allocateFromString(startTime);
            MemorySegment endDateInput = memoryAllocator.allocateFromString(endDate);
            MemorySegment endTimeInput = memoryAllocator.allocateFromString(endTime);
            MemorySegment numberValuesOutput = memoryAllocator.allocateInts(1);
            MemorySegment qualityWidthOutput = memoryAllocator.allocateInts(1);

            int status = hecdss_h.hec_dss_tsGetSizes(
                    dssPointer,
                    dssPathnameInput,
                    startDateInput,
                    startTimeInput,
                    endDateInput,
                    endTimeInput,
                    numberValuesOutput,
                    qualityWidthOutput
            );

            if (status == 0) {
                int numberValues = MemoryParser.parseInt(numberValuesOutput);
                int qualityWidth = MemoryParser.parseInt(qualityWidthOutput);
                return new int[] {numberValues, qualityWidth};
            } else {
                String message = formatLogMessage("Failed tsGetSizes", dssFileName, dssPathname);
                logger.warning(message);
            }
        } catch (Exception exception) {
            logger.severe(exception.getMessage());
        }

        return new int[] {0, 0};
    }

    /* Validation */
    private static boolean validateOutputs(int[] timeArray, double[] valueArray, int numberValuesRead) {
        if (timeArray.length != valueArray.length) {
            logger.warning("Time & Value Array Length Mismatch");
            return false;
        }

        if (timeArray.length != numberValuesRead) {
            logger.warning("Time & Value Array Length is different than number of values read");
            return false;
        }

        return true;
    }

    /* Helpers */
    private static String formatLogMessage(String message, String... inputs) {
        return message + ": " + String.join(" | ", inputs);
    }
}
