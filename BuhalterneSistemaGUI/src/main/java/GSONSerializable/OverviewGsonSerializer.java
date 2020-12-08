package GSONSerializable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dataModels.Overview;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;

public class OverviewGsonSerializer implements JsonSerializer<Overview> {
    @Override
    public JsonElement serialize(Overview overview, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String date = overview.getDate().format(formatter);

        json.addProperty("date",  date);
        json.addProperty("amount", overview.getAmount());
        json.addProperty("description", overview.getDescription());

        return json;
    }
}
