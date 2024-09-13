package mil.army.usace.hec.dss.internal;

record RawDssGrid(
        int type,
        int dataType,
        int lowerLeftCellX,
        int lowerLeftCellY,
        int numberOfCellsX,
        int numberOfCellsY,
        int numberOfRanges,
        int srsDefinitionType,
        int timeZoneRawOffset,
        int isInterval,
        int isTimeStamped,
        String dataUnits,
        String dataSource,
        String srsName,
        String srsDefinition,
        String timeZoneId,
        float cellSize,
        float xCoordOfGridCellZero,
        float yCoordOfGridCellZero,
        float nullValue,
        float maxDataValue,
        float minDataValue,
        float meanDataValue,
        float rangeLimitTable,
        float numberEqualOrExceedingRangeLimit,
        float[] data)
{
}
