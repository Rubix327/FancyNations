package me.rubix327.fancynations.data;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import me.rubix327.fancynations.util.Utils;
import org.bukkit.Location;

@Converter(autoApply = true)
public class LocationConverter implements AttributeConverter<Location, String>{

    @Override
    public String convertToDatabaseColumn(Location location) {
        return Utils.serializeLocation(location);
    }

    @Override
    public Location convertToEntityAttribute(String location) {
        return Utils.deserializeLocation(location);
    }

}
