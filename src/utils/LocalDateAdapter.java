package utils;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Ο adapter LocalDateAdapter χρησιμοποιείται για την (α)σειριακή και (α)δειακή μετατροπή αντικειμένων LocalDate σε JSON και αντίστροφα,
 * χρησιμοποιώντας το ISO_LOCAL_DATE format.
 */
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Μετατρέπει ένα αντικείμενο LocalDate σε JSON.
     *
     * @param date το LocalDate που πρόκειται να σειριοποιηθεί.
     * @param typeOfSrc ο τύπος του αντικειμένου (δεν χρησιμοποιείται).
     * @param context το πλαίσιο σειριοποίησης.
     * @return ένα JsonElement που περιέχει το μορφοποιημένο LocalDate.
     */
    @Override
    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(date.format(formatter));
    }

    /**
     * Ανασσειριοποιεί ένα JsonElement σε αντικείμενο LocalDate.
     *
     * @param json το JsonElement που περιέχει την ημερομηνία.
     * @param typeOfT ο τύπος του αντικειμένου που αναμένεται (δεν χρησιμοποιείται).
     * @param context το πλαίσιο αποσειριοποίησης.
     * @return ένα αντικείμενο LocalDate που προκύπτει από την ανάλυση του JSON.
     * @throws JsonParseException αν η ανάλυση δεν είναι δυνατή.
     */
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalDate.parse(json.getAsString(), formatter);
    }
}
