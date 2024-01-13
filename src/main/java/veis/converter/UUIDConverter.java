package veis.converter;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDConverter {
    public static UUID convertByteArrayToUUID(byte[] byteArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        long mostSignificantBits = byteBuffer.getLong();
        long leastSignificantBits = byteBuffer.getLong();
        return new UUID(mostSignificantBits, leastSignificantBits);
    }
}
