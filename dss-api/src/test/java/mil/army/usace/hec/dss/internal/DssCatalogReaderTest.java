package mil.army.usace.hec.dss.internal;

import mil.army.usace.hec.dss.TestUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DssCatalogReaderTest {
    @Test
    void CatalogRetrieveAll() {
        String dssFilePath = TestUtil.getResourceFile("examples-all-data-types.dss").toString();
        String dssPathFilter = "*";
        List<String> catalogPathnameList = DssCatalogReader.getCatalog(dssFilePath, dssPathFilter);
        assertEquals(208, catalogPathnameList.size());
    }
}