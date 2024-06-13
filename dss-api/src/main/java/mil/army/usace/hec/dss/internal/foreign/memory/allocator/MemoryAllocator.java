package mil.army.usace.hec.dss.internal.foreign.memory.allocator;

import mil.army.usace.hec.dss.internal.foreign.ForeignLanguage;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public interface MemoryAllocator {
    MemorySegment allocateChars(int characterCount);
    MemorySegment allocateInts(int integerCount);
    MemorySegment allocateDoubles(int doubleCount);
    MemorySegment allocateFromString(String stringToAllocate);

    static MemoryAllocator create(ForeignLanguage foreignLanguage, Arena memorySession) {
        return new MemoryAllocatorImpl(foreignLanguage, memorySession);
    }
}
