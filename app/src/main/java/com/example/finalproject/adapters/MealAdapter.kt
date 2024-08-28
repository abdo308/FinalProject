package com.example.finalproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.network.Meal

class MealAdapter(private val meals: Meal,private val context: View) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {
    private var isLiked = false

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
        val meal = meals
        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .placeholder(R.drawable.baseline_downloading_24)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.mealImage)

        holder.title.text=meal.strMeal
        holder.mealImage.setOnClickListener {
            Navigation.findNavController(context).navigate(R.id.action_from_home_to_details)
        }
        holder.heartIcon.setImageResource(
            if (isLiked) R.drawable.heart_dark else R.drawable.heart
        )
        holder.heartIcon.setOnClickListener {
            isLiked=!isLiked
            if(isLiked)
                holder.heartIcon.setImageResource(R.drawable.heart)
            else
                holder.heartIcon.setImageResource(R.drawable.heart_dark)
        }
    }

    override fun getItemCount(): Int =  1
}
