package api;

import java.time.Instant;

public interface DssTimeSeries {
    Instant[] times();
    double[] values();
}
