package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // JSON Keys
    private static String JSON_KEY_NAME = "name";
    private static String JSON_KEY_SANDWICH_MAIN_NAME = "mainName";
    private static String JSON_KEY_SANDWICH_ALSO_KNOWN_AS = "alsoKnownAs";
    private static String JSON_KEY_SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static String JSON_KEY_SANDWICH_DESCRIPTION = "description";
    private static String JSON_KEY_SANDWICH_IMAGE = "image";
    private static String JSON_KEY_SANDWICH_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {


        try {
            JSONObject jsonObject = new JSONObject(json);

            Sandwich sandwich = new Sandwich();

            // Name
            if (jsonObject.has(JSON_KEY_NAME)) {
                JSONObject name = jsonObject.getJSONObject("name");

                sandwich.setMainName(name.optString(JSON_KEY_SANDWICH_MAIN_NAME));

                // Also known as
                List<String> alsoKnownAs = parseJsonArray(name.getJSONArray(JSON_KEY_SANDWICH_ALSO_KNOWN_AS));
                sandwich.setAlsoKnownAs(alsoKnownAs);
            }

            // Place of Origin
            if (jsonObject.has(JSON_KEY_SANDWICH_PLACE_OF_ORIGIN)) {
                sandwich.setPlaceOfOrigin(jsonObject.optString("placeOfOrigin"));
            }

            // Description
            if (jsonObject.has(JSON_KEY_SANDWICH_DESCRIPTION)) {
                sandwich.setDescription(jsonObject.optString(JSON_KEY_SANDWICH_DESCRIPTION));
            }

            // Image
            if (jsonObject.has(JSON_KEY_SANDWICH_IMAGE)) {
                sandwich.setImage(jsonObject.optString(JSON_KEY_SANDWICH_IMAGE));
            }

            // Ingredients
            if (jsonObject.has(JSON_KEY_SANDWICH_INGREDIENTS)) {
                List<String> ingredients = parseJsonArray(jsonObject.getJSONArray("ingredients"));
                sandwich.setIngredients(ingredients);
            }

            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }

    private static List<String> parseJsonArray(JSONArray array) {
        List<String> items = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            items.add(array.optString(i));
        }

        return items;
    }
}
