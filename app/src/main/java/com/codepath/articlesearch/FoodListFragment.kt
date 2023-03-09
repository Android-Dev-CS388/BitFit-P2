package com.codepath.articlesearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.articlesearch.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.coroutines.launch
import okhttp3.Headers
import org.json.JSONException


class FoodListFragment : Fragment() {

    private val foods = mutableListOf<DisplayFood>()
    private lateinit var foodsRecyclerView: RecyclerView
    private lateinit var foodAdapter: FoodAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_food_list, container, false)

        // Add these configurations for the recyclerView and to configure the adapter
        val layoutManager = LinearLayoutManager(context)
        foodsRecyclerView = view.findViewById(R.id.food_recycler_view)
        foodsRecyclerView.layoutManager = layoutManager
        foodsRecyclerView.setHasFixedSize(true)
        foodAdapter = FoodAdapter(view.context, foods)
        foodsRecyclerView.adapter = foodAdapter

        val button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener{
            val intent = Intent(context, DetailActivity::class.java)
//            intent.(FOOD_EXTRA, button,/)
            context?.startActivity(intent)
        }


        // Update the return statement to return the inflated view from above
        return view
    }

    companion object {
        fun newInstance() : FoodListFragment {
            return FoodListFragment()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Call the new method within onViewCreated
        fetchFoods()
    }

    private fun fetchFoods() {
        lifecycleScope.launch {
            (activity?.application as FoodApplication).db.foodDao().getAll().collect { databaseList ->
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
    }

}