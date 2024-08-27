package com.example.finalproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.network.Meal

// Still Trying to do it
class MealAdapterCollection(private val meals:List<Meal>): RecyclerView.Adapter<MealAdapterCollection.MealViewHolder>() {
    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mealImage: ImageView = itemView.findViewById(R.id.imageView)
        var title=itemView.findViewById<TextView>(R.id.cardText)
        var heartIcon=itemView.findViewById<ImageButton>(R.id.imageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_home, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.mealImage)

        holder.title.text=meal.strMeal
    }

    override fun getItemCount(): Int =  1
}