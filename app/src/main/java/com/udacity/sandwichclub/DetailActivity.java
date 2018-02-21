package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // Text views
    TextView mOriginTvTextView;
    TextView mAlsoKnownAsTextView;
    TextView mIngredientsTextView;
    TextView mPlaceOfOriginTextView;
    TextView mDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Image view
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        // Text views
        mOriginTvTextView = findViewById(R.id.origin_tv);
        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mPlaceOfOriginTextView = findViewById(R.id.place_of_origin_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mOriginTvTextView.setText(sandwich.getMainName());
        mAlsoKnownAsTextView.setText(listToString(sandwich.getAlsoKnownAs()));
        mIngredientsTextView.setText(listToString(sandwich.getIngredients()));
        mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        mDescriptionTextView.setText(sandwich.getDescription());
    }

    private static String listToString(List<String> items) {
        if (items == null || items.size() == 0) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        String SEPARATOR = ", ";

        for(String item: items) {
            stringBuilder.append(item);
            stringBuilder.append(SEPARATOR);
        }

        // Remove last comma
        String output = stringBuilder.toString();

        if (output.length() > SEPARATOR.length()) {
            try {
                output = output.substring(0, output.length() - SEPARATOR.length());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return output;
    }
}
