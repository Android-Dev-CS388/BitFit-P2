package com.codepath.articlesearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.articlesearch.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
//private const val SEARCH_API_KEY = BuildConfig.API_KEY
//private const val ARTICLE_SEARCH_URL =
//    "https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=${SEARCH_API_KEY}"

class MainActivity : AppCompatActivity() {
    private val foods = mutableListOf<DisplayFood>()
    private lateinit var foodsRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        foodsRecyclerView = findViewById(R.id.foods)
        // TODO: Set up ArticleAdapter with articles
        val foodAdapter = FoodAdapter(this, foods)
        foodsRecyclerView.adapter = foodAdapter

        lifecycleScope.launch {
            (application as FoodApplication).db.foodDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayFood(
                        entity.food,
                        entity.calories
                    )
                }.also { mappedList ->
                    foods.clear()
                    foods.addAll(mappedList)
                    foodAdapter.notifyDataSetChanged()
                }
            }
        }

        foodsRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            foodsRecyclerView.addItemDecoration(dividerItemDecoration)
        }


        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener{
            val intent = Intent(this, DetailActivity::class.java)
//            intent.(FOOD_EXTRA, button,/)
            this.startActivity(intent)
        }
//        val client = AsyncHttpClient()
//        client.get(FOOD_SEARCH_URL, object : JsonHttpResponseHandler() {
//            override fun onFailure(
//                statusCode: Int,
//                headers: Headers?,
//                response: String?,
//                throwable: Throwable?
//            ) {
//                Log.e(TAG, "Failed to fetch articles: $statusCode")
//            }
//
//            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
//                Log.i(TAG, "Successfully fetched articles: $json")
//                try {
//                    // TODO: Create the parsedJSON
//
//                    // TODO: Do something with the returned json (contains article information)
//                    val parsedJson = createJson().decodeFromString(
//                        SearchNewsResponse.serializer(),
//                        json.jsonObject.toString()
//                    )
//                    // TODO: Save the articles and reload the screen
//                    parsedJson.response?.docs?.let { list ->
//                        lifecycleScope.launch(IO) {
//                            (application as ArticleApplication).db.articleDao().deleteAll()
//                            (application as ArticleApplication).db.articleDao().insertAll(list.map {
//                                ArticleEntity(
//                                    headline = it.headline?.main,
//                                    articleAbstract = it.abstract,
//                                    byline = it.byline?.original,
//                                    mediaImageUrl = it.mediaImageUrl
//                                )
//                            })
//                        }
//                    }
//
//                } catch (e: JSONException) {
//                    Log.e(TAG, "Exception: $e")
//                }
//            }
//
//        })

    }
}