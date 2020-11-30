package GSONSerializable;

import com.google.gson.*;
import dataStructures.FinanceManagementSystem;
import dataStructures.Income;

import java.lang.reflect.Type;
import java.util.List;

public class AllIncomeGsonSerializer implements JsonSerializer<List<Income>> {
    @Override
    public JsonElement serialize(List<Income> incomes, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Income.class, new FmsGsonSerializer());
        Gson parser = gsonBuilder.create();

        for (Income income : incomes) {
            jsonArray.add(parser.toJson(income));
        }

        return jsonArray;
    }
}