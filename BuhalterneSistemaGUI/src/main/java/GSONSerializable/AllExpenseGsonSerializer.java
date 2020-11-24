package GSONSerializable;

import com.google.gson.*;
import dataStructures.Expense;

import java.lang.reflect.Type;
import java.util.List;

public class AllExpenseGsonSerializer implements JsonSerializer<List<Expense>> {
    @Override
    public JsonElement serialize(List<Expense> expenses, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Expense.class, new ExpenseGsonSerializer());
        Gson parser = gsonBuilder.create();

        for(Expense expense : expenses) {
            jsonArray.add(parser.toJson(expense));
        }

        return jsonArray;
    }
}
