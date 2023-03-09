package com.codepath.articlesearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private val foods = mutableListOf<DisplayFood>()
    private var total = 0
    private var min = 100000000
    private var max = -1
    private var length = 0
    private var average = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val averageCal = view?.findViewById<TextView>(R.id.averageCal)
        val maxCal = view?.findViewById<TextView>(R.id.maxCal)
        val minCal = view?.findViewById<TextView>(R.id.minCal)

        averageCal?.text = "Average Calories: " + average.toString()
        maxCal?.text = "Max Calories: " + max.toString()
        minCal?.text = "Min Calories: " + min.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_dashboard, container, false)

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val button = view.findViewById<Button>(R.id.button)
        val clearButton = view.findViewById<Button>(R.id.clearButton)

        button.setOnClickListener{
            val intent = Intent(context, DetailActivity::class.java)
//            intent.(FOOD_EXTRA, button,/)
            context?.startActivity(intent)
        }
        clearButton.setOnClickListener{
            lifecycleScope.launch {
                (activity?.application as FoodApplication).db.foodDao().deleteAll()
            }
        }

        return view
    }

    companion object {
        fun newInstance() : DashboardFragment {
            return DashboardFragment()
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
//                    DisplayFood(
//                        entity.food,
//                        entity.calories
//                    )
                    var cal = Integer.parseInt(entity.calories.toString())
                    total += cal
                    if(cal > max) {
                        max = cal
                    }
                    if(cal < min) {
                        min = cal
                    }
                    length++;
                }.also {
                    average = total/length
                    val averageCal = view?.findViewById<TextView>(R.id.averageCal)
                    val maxCal = view?.findViewById<TextView>(R.id.maxCal)
                    val minCal = view?.findViewById<TextView>(R.id.minCal)

                    averageCal?.text = "Average Calories: " + average.toString()
                    maxCal?.text = "Max Calories: " + max.toString()
                    minCal?.text = "Min Calories: " + min.toString()
                    Log.d("TEST", "Average " + average.toString() + " Total " + total.toString() + " Min " + min.toString() + " Max " + max.toString())
                }
            }
        }
    }
}