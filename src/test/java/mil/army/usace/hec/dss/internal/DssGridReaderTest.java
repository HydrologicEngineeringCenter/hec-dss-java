package mil.army.usace.hec.dss.internal;

import mil.army.usace.hec.dss.TestUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DssGridReaderTest {
    @Test
    void RetrieveGriddedData() {
        String dssFileName = TestUtil.getResourceFile("examples-all-data-types.dss").toString();
        String dssPathname = "/grid/EAU GALLA RIVER/SNOW MELT/02FEB2020:0600/03FEB2020:0600/SHG-SNODAS/";

        DssGrid dssGrid = DssGridReader.getGriddedData(dssFileName, dssPathname);

        assertEquals(420, dssGrid.type());
        assertEquals(588, dssGrid.data().length);
    }
}