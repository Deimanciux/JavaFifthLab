package GSONSerializable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dataModels.Balance;

import java.lang.reflect.Type;

public class BalanceGsonSerializer implements JsonSerializer<Balance> {
    @Override
    public JsonElement serialize(Balance balance, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        json.addProperty("income", balance.getIncome());
        json.addProperty("expense", balance.getExpense());

        return json;
    }
}
