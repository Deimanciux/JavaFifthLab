package GSONSerializable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dataStructures.Income;

import java.lang.reflect.Type;

public class IncomeGsonSerializer implements JsonSerializer<Income> {
    @Override
    public JsonElement serialize(Income income, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        json.addProperty("id", income.getId());
        json.addProperty("amount", income.getAmount());
        json.addProperty("description", income.getDescription());

        return json;
    }
}
