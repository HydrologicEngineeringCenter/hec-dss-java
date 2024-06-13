package mil.army.usace.hec.dss.api;

import java.util.stream.Stream;

public interface DssDataReader {
    Stream<String> getDssCatalog();
}
