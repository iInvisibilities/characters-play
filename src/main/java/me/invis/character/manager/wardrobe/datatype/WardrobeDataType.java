package me.invis.character.manager.wardrobe.datatype;

import me.invis.character.manager.wardrobe.impl.Wardrobe;
import org.apache.commons.lang3.SerializationUtils;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class WardrobeDataType implements PersistentDataType<byte[], Wardrobe> {

    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public Class<Wardrobe> getComplexType() {
        return Wardrobe.class;
    }

    @Override
    public byte[] toPrimitive(Wardrobe wardrobe, PersistentDataAdapterContext persistentDataAdapterContext) {
        return SerializationUtils.serialize(wardrobe);
    }

    @Override
    public Wardrobe fromPrimitive(byte[] bytes, PersistentDataAdapterContext persistentDataAdapterContext) {
        try {
            InputStream inputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return (Wardrobe) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
