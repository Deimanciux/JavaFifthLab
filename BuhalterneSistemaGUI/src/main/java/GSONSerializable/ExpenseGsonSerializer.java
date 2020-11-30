package GSONSerializable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dataStructures.Expense;

import java.lang.reflect.Type;

public class ExpenseGsonSerializer implements JsonSerializer<Expense> {
    @Override
    public JsonElement serialize(Expense expense, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        json.addProperty("id", expense.getId());
        json.addProperty("amount", expense.getAmount());
        json.addProperty("description", expense.getDescription());

        return json;
    }
}