package com.codepath.articlesearch

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

const val FOOD_EXTRA = "FOOD_EXTRA"
private const val TAG = "FoodAdapter"

class FoodAdapter(private val context: Context, private val foods: List<DisplayFood>) :
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO: Get the individual article and bind to holder
        val food = foods[position]
        holder.bind(food)
    }

    override fun getItemCount() = foods.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val foodTextView = itemView.findViewById<TextView>(R.id.food)
        private val caloriesTextView = itemView.findViewById<TextView>(R.id.calories)



        init {
            itemView.setOnClickListener(this)
        }

        // TODO: Write a helper method to help set up the onBindViewHolder method
        fun bind(food: DisplayFood) {
            foodTextView.text = food.food
            caloriesTextView.text = food.calories.toString()
        }

        override fun onClick(v: View?) {
            // TODO: Get selected article
            val food = foods[absoluteAdapterPosition]

            // TODO: Navigate to Details screen and pass selected article
//            val intent = Intent(context, DetailActivity::class.java)
//            intent.putExtra(FOOD_EXTRA, food)
//            context.startActivity(intent)
        }

    }
}

