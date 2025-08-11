package mil.army.usace.hec.dss.internal;

import apifinal.*;
import apifinal.DssTimeSeries;
import apifinal.HecDss;

import java.util.stream.Stream;

public record HecDssImpl(
        DssSession dssSession
) implements HecDss {
    public static HecDss open(String dssFileName) {
        return new HecDssImpl(DssSession.initiate(dssFileName));
    }

    @Override
    public void setGlobalDebugLevel(DebugLevel level) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDebugLevel(DebugLevel level) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DssRecordType getRecordType(DssPathname pathname) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<DssPathname> getCatalog() {
        return Stream.empty();
    }

    @Override
    public int getRecordCount() {
        return 0;
    }

    @Override
    public DssTimeSeries getTimeSeries(DssPathname pathname) {
        return null;
    }

    @Override
    public DssTimeSeries getTimeSeries(DssPathname pathname, DssTimeWindow timeWindow) {
        return null;
    }

    @Override
    public DssPairedData getPairedData(DssPathname pathname) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DssGriddedData getGriddedData(DssPathname pathname) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DssArrayContainer getArray(DssPathname pathname) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DssLocationInfo getLocationInfo(DssPathname pathname) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void put(DssTimeSeries timeSeries) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void put(DssPairedData pairedData) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void put(DssGriddedData griddedData) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void put(DssArrayContainer arrayContainer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void put(DssLocationInfo locationInfo) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(DssPathname pathname) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(DssPathname pathnamePattern) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteTimeSeriesRange(DssPathname pathname, DssTimeWindow timeWindow) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
        dssSession.close();
    }
}
