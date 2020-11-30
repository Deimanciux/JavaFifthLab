package GSONSerializable;

import com.google.gson.*;
import dataStructures.FinanceManagementSystem;

import java.lang.reflect.Type;
import java.util.List;

public class AllFmsGsonSerializer implements JsonSerializer<List<FinanceManagementSystem>> {
    @Override
    public JsonElement serialize(List<FinanceManagementSystem> financeManagementSystems, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(FinanceManagementSystem.class, new FmsGsonSerializer());
        Gson parser = gsonBuilder.create();

        for (FinanceManagementSystem fms : financeManagementSystems) {
            jsonArray.add(parser.toJson(fms));
        }

        return jsonArray;
    }
}