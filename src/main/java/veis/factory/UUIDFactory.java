package veis.factory;

import veis.AbstractUUIDGenerator;
import veis.converter.HexConverter;
import veis.converter.UUIDConverter;

import java.util.UUID;

public class UUIDFactory {

    private final AbstractUUIDGenerator generator;

    public UUIDFactory(AbstractUUIDGenerator generator) {
        this.generator = generator;
    }

    /**
     * Create Efficient UUID And Convert to UUID Type
     */
    public UUID generateUUID() {
        byte[] byteArray = generator.createUUID();
        return UUIDConverter.convertByteArrayToUUID(byteArray);
    }

    public String generateUUIDHex() {
        byte[] byteArray = generator.createUUID();
        return HexConverter.bytesToHex(byteArray);
    }
}
