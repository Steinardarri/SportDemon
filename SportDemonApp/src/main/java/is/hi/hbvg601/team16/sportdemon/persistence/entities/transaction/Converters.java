package is.hi.hbvg601.team16.sportdemon.persistence.entities.transaction;

import androidx.room.TypeConverter;
import java.time.*;

public class Converters {

    @TypeConverter
    public static LocalDate fromTimestamp(Long value) {
        return value == null ? null : LocalDate.ofEpochDay(value);
    }
    @TypeConverter
    public static Long dateToTimestamp(LocalDate date) {
        return date == null ? null : date.toEpochDay();
    }

}
