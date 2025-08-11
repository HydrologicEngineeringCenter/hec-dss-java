package api;

import mil.army.usace.hec.dss.internal.HecDssImpl;

import java.util.stream.Stream;

/**
 * Main interface for working with DSS files.
 */
public interface HecDss extends AutoCloseable {

    /**
     * Sets the global debug level for the DSS library.
     */
    void setGlobalDebugLevel(DebugLevel level);

    /**
     * Sets the debug level for this DSS instance.
     */
    void setDebugLevel(DebugLevel level);

    /**
     * Gets the record type for a given DSS pathname.
     */
    DssRecordType getRecordType(DssPathname pathname);

    /**
     * Gets a stream of all pathnames in the DSS file.
     */
    Stream<DssPathname> getCatalog();

    /**
     * Gets the number of records stored in the DSS file.
     */
    int getRecordCount();

    /**
     * Retrieves time series data from the DSS file.
     */
    DssTimeSeries getTimeSeries(DssPathname pathname);

    /**
     * Retrieves time series data from the DSS file within a time window.
     */
    DssTimeSeries getTimeSeries(DssPathname pathname, DssTimeWindow timeWindow);

    /**
     * Retrieves paired data from the DSS file.
     */
    DssPairedData getPairedData(DssPathname pathname);

    /**
     * Retrieves gridded data from the DSS file.
     */
    DssGriddedData getGriddedData(DssPathname pathname);

    /**
     * Retrieves array data from the DSS file.
     */
    DssArrayContainer getArray(DssPathname pathname);

    /**
     * Retrieves location information from the DSS file.
     */
    DssLocationInfo getLocationInfo(DssPathname pathname);

    /**
     * Stores data in the DSS file.
     */
    void put(DssTimeSeries timeSeries);

    /**
     * Stores data in the DSS file.
     */
    void put(DssPairedData pairedData);

    /**
     * Stores data in the DSS file.
     */
    void put(DssGriddedData griddedData);

    /**
     * Stores data in the DSS file.
     */
    void put(DssArrayContainer arrayContainer);

    /**
     * Stores data in the DSS file.
     */
    void put(DssLocationInfo locationInfo);

    /**
     * Deletes a record from the DSS file.
     */
    void delete(DssPathname pathname);

    /**
     * Deletes all records matching the given pathname pattern.
     */
    void deleteAll(DssPathname pathnamePattern);

    /**
     * Deletes time series data within the specified time window.
     */
    void deleteTimeSeriesRange(DssPathname pathname, DssTimeWindow timeWindow);

    /**
     * Closes the DSS file and releases any locks.
     */
    @Override
    void close();

    /**
     * Creates a new HecDss instance for the specified file.
     */
    static HecDss open(String filename) {
        return HecDssImpl.open(filename);
    }
}