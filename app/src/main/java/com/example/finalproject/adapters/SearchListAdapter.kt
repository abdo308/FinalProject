package com.example.finalproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.db.FavouritesViewModel
import com.example.finalproject.db.FavouritesViewModelFactory
import com.example.finalproject.db.LocalDataSourceImpl
import com.example.finalproject.models.UserFavourites
import com.example.finalproject.network.Meal
import com.example.finalproject.ui.SearchFragmentDirections
import java.security.acl.Owner

class SearchListAdapter(
    private val meals:List<Meal>,
    private val view: View,
    private val context: Context,
    private val favViewModel : FavouritesViewModel,
    private val owner : LifecycleOwner
    ): RecyclerView.Adapter<SearchListAdapter.SearchItemViewHolder>() {
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
        var liked :Boolean
        val sharedPreferences = view.context.getSharedPreferences("key", Context.MODE_PRIVATE)
        val email=sharedPreferences.getString("Email","NoEmail")
        if(email != null) {
            favViewModel.addUser(UserFavourites(email, emptyList<Meal>().toMutableList()))
            favViewModel.getFavList(email)
        }
        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .placeholder(R.drawable.baseline_downloading_24)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.mealImage)
        holder.title.text=meal.strMeal
        holder.mealImage.setOnClickListener {
            val direction = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(meals[holder.adapterPosition])
            Navigation.findNavController(view).navigate(direction)
        }
        favViewModel.favourites.observe(owner) { favs ->
            val favourites = favs
            val isLiked = favourites.find { id == it.idMeal }
            if (isLiked == null) {
                holder.heartIcon.setImageResource(R.drawable.baseline_favorite_border_24)
                liked = false
            } else {
                holder.heartIcon.setImageResource(R.drawable.baseline_favorite_24)
                liked = true
            }
            holder.heartIcon.setOnClickListener {
                if (liked) {
                    liked = false
                    holder.heartIcon.setImageResource(R.drawable.baseline_favorite_border_24)
                    favourites.remove(meal)
                }
                else {
                    liked = true
                    holder.heartIcon.setImageResource(R.drawable.baseline_favorite_24)
                    favourites.add(meal)
                }
                if (email != null)
                    favViewModel.updateFavList(email,favourites)
            }

        }

    }

    override fun getItemCount(): Int =  meals.size
}