package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwich = new JSONObject(json);

            // Context context = Contex

            // Name
            JSONObject name = sandwich.getJSONObject("name");

            // Main name
            String mainName = name.getString("mainName");

            // Also known as
            List<String> alsoKnownAs = parseJsonArray(name.getJSONArray("alsoKnownAs"));

            // Place of Origin
            String placeOfOrigin = sandwich.getString("placeOfOrigin");

            // Description
            String description = sandwich.getString("description");

            // Image
            String image = sandwich.getString("image");

            // Ingredients
            List<String> ingredients = parseJsonArray(sandwich.getJSONArray("ingredients"));

            return new Sandwich(
                    mainName,
                    alsoKnownAs,
                    placeOfOrigin,
                    description,
                    image,
                    ingredients
            );

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }

    private static List<String> parseJsonArray(JSONArray array) {
        List<String> items = new ArrayList<>();

        try {
            for (int i = 0; i < array.length(); i++) {
                items.add(array.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return items;
    }
}
