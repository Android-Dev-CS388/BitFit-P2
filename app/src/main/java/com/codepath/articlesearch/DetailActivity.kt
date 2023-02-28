package com.codepath.articlesearch

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var foodTextView: TextView
    private lateinit var caloriesTextView: TextView
    private lateinit var foodInput: EditText
    private lateinit var caloriesInput: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // TODO: Find the views for the screen
        foodTextView = findViewById(R.id.food)
        caloriesTextView = findViewById(R.id.calories)
        foodInput = findViewById(R.id.foodInputText)
        caloriesInput = findViewById(R.id.caloriesInputText)

        val recordButton = findViewById<Button>(R.id.recordButton);
        recordButton.setOnClickListener{

            Log.d("Test","Got here")
            lifecycleScope.launch(Dispatchers.IO) {
                    (application as FoodApplication).db.foodDao().insertAll(
                    FoodEntity(
                        food = foodInput.text.toString(),
                        calories = caloriesInput.text.toString().toInt()
                    )
                )
            }
            finish();
        }
        // TODO: Get the extra from the Intent
//        val food = intent.getSerializableExtra(FOOD_EXTRA) as DisplayFood

        // TODO: Set the title, byline, and abstract information from the article
//        foodTextView.text = food.food
//        caloriesTextView.text = food.calories.toString()
//
//        foodTextView.text = "NAME"
//        caloriesTextView.text = "CALORIES"

        // TODO: Load the media image

    }
}