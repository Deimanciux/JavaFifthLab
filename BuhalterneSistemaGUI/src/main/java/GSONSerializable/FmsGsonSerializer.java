package GSONSerializable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dataStructures.FinanceManagementSystem;

import java.lang.reflect.Type;

public class FmsGsonSerializer implements JsonSerializer<FinanceManagementSystem> {
    @Override
    public JsonElement serialize(FinanceManagementSystem financeManagementSystem, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject fmsJson = new JsonObject();
        fmsJson.addProperty("id", financeManagementSystem.getId());
        fmsJson.addProperty("company", financeManagementSystem.getCompany());
        fmsJson.addProperty("systemVersion", financeManagementSystem.getSystemVersion());

        return fmsJson;
    }
}
