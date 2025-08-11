package mil.army.usace.hec.dss.internal;

import api.*;
import api.DssTimeSeries;
import api.HecDss;

import java.util.stream.Stream;

public final class HecDssImpl implements HecDss {
    private final DssSession dssSession;
    private final DssCatalogService dssCatalogService;
    private final DssTimeSeriesService dssTimeSeriesService;

    private HecDssImpl(String dssFileName) {
        dssSession = DssSession.initiate(dssFileName);
        dssCatalogService = new DssCatalogService(dssSession);
        dssTimeSeriesService = new DssTimeSeriesService(dssSession);
    }

    public static HecDss open(String dssFileName) {
        return new HecDssImpl(dssFileName);
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
        return dssCatalogService.getCatalog();
    }

    @Override
    public int getRecordCount() {
        return dssCatalogService.getRecordCount();
    }

    @Override
    public DssTimeSeries getTimeSeries(DssPathname pathname) {
        return dssTimeSeriesService.getTimeSeries(pathname);
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
