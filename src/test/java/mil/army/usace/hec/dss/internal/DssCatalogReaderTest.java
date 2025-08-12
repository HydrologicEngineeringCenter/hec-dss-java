package mil.army.usace.hec.dss.internal;

import api.DssPathname;
import api.HecDss;
import mil.army.usace.hec.dss.TestUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DssCatalogReaderTest {
    @Test
    void CatalogRetrieveAll() {
        String dssFilePath = TestUtil.getResourceFile("examples-all-data-types.dss").toString();

        try (HecDss hecDss = HecDss.open(dssFilePath)) {
            List<DssPathname> catalogPathnameList = hecDss.getCatalog().toList();
            assertEquals(208, catalogPathnameList.size());
        }
    }
}