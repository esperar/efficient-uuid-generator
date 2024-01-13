package veis.uuid1;

import com.fasterxml.uuid.Generators;
import veis.AbstractUUIDGenerator;

import java.nio.ByteBuffer;
import java.util.UUID;

public class EfficientUUID1Generator implements AbstractUUIDGenerator {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    /**
     * UUID V1 is created with the following information in each field:
     * `Timestamp` - `Timestamp` - `Timestamp & Version` - `Variant & Clock sequence` - `Node id`
     *
     * As it is a value generated based on time,
     * arranging the order well allows for the creation of as sequential a value as possible.
     *
     * In summary, by arranging the fields in the order of 3 - 2 - 1 - 4 - 5,
     * it is possible to have sequential values up to a certain point in the 3rd field. (This is not an absolute rule.)
     *
     * @return uuid byte[]
     */
    @Override
    public byte[] createUUID() {
        UUID uuidV1 = Generators.timeBasedGenerator().generate();
        String[] uuidV1Parts = uuidV1.toString().split("-");
        String sequentialUUID = uuidV1Parts[2] + uuidV1Parts[1] + uuidV1Parts[0] + uuidV1Parts[3] + uuidV1Parts[4];

        String sequentialUuidV1 = String.join("", sequentialUUID);
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(Long.parseUnsignedLong(sequentialUuidV1.substring(0, 16), 16));
        bb.putLong(Long.parseUnsignedLong(sequentialUuidV1.substring(16), 16));
        return bb.array();
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];

        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }

        return new String(hexChars).toLowerCase();
    }

}
