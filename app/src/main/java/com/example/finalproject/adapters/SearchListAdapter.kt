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
import com.example.finalproject.ui.SearchFragmentDirections

class SearchListAdapter(private val meals:List<Meal>, private val context: View): RecyclerView.Adapter<SearchListAdapter.SearchItemViewHolder>() {
    private val likeMap:MutableMap<String?,Boolean> = mutableMapOf()
    inner class SearchItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mealImage: ImageView = itemView.findViewById(R.id.searchView)
        var title=itemView.findViewById<TextView>(R.id.searchCardText)
        var heartIcon=itemView.findViewById<ImageButton>(R.id.searchImageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_search, parent, false)
        return SearchItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val meal = meals[position]
        val id=meal.idMeal
        val isLiked=likeMap[id] ?:false

        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .placeholder(R.drawable.baseline_downloading_24)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.mealImage)
        holder.title.text=meal.strMeal
        holder.mealImage.setOnClickListener {
            val direction = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(meals[holder.adapterPosition])
            Navigation.findNavController(context).navigate(direction)
        }


        holder.heartIcon.setImageResource(
            if (isLiked) R.drawable.heart_dark else R.drawable.heart
        )
        holder.heartIcon.setOnClickListener {
            val currentLike = likeMap[id] ?: false
            val newLike = !currentLike
            likeMap[id] = newLike
            holder.heartIcon.setImageResource(
                if (newLike) R.drawable.heart else R.drawable.heart_dark
            )
        }
    }

    override fun getItemCount(): Int =  meals.size
}