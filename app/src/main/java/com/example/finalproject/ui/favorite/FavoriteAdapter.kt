package com.example.finalproject.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.CardviewFavoriteBinding
import com.example.finalproject.network.Meal

class FavoriteAdapter(
    private val updateListCallback: (MutableList<Meal>) -> Unit
) :
    ListAdapter<Meal, FavoriteAdapter.FavoriteViewHolder>(FavoriteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemBinding =
            CardviewFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FavoriteViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoriteViewHolder(
        private val itemBinding: CardviewFavoriteBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(meal: Meal) {
            Glide.with(itemView.context).load(meal.strMealThumb)
                .into(itemBinding.mealThumbImageView)

            itemBinding.mealNameTextView.text = meal.strMeal
            itemBinding.mealCategoryTextView.text = meal.strCategory
            itemBinding.mealAreaTextView.text = meal.strArea

            itemBinding.favoriteButton.setOnClickListener {
                deleteItem(adapterPosition)
            }

            itemBinding.favoritesItemCardView.setOnClickListener{
                val directions = FavoriteFragmentDirections.actionFavoriteFragmentToDetailsFragment(meal)
                it.findNavController().navigate(directions)
            }
        }
    }

    // Function to delete an item
    private fun deleteItem(position: Int) {
        val currentList = currentList.toMutableList()

        if (position >= 0 && position < currentList.size) {
            currentList.removeAt(position)
            submitList(currentList)
            updateListCallback(currentList)
        }
    }

    class FavoriteDiffCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal) =
            oldItem.idMeal == newItem.idMeal

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal) = oldItem == newItem
    }
}