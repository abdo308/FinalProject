package com.example.finalproject.ui.favorite

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.R
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
                showFavConfirmationMessage(itemView.context)
            }

            itemBinding.favoritesItemCardView.setOnClickListener {
                val directions =
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailsFragment(meal)
                it.findNavController().navigate(directions)
            }
        }

        private fun showFavConfirmationMessage(context: Context) {
            val dialogView = LayoutInflater.from(context)
                .inflate(R.layout.confirmation_message_view, null)
            val builder = AlertDialog.Builder(context)
            builder.setView(dialogView)
            val dialog = builder.create()
            val message = dialogView.findViewById<TextView>(R.id.messageText)
            val title = dialogView.findViewById<TextView>(R.id.titleText)
            val yesButton = dialogView.findViewById<Button>(R.id.button_yes)
            val noButton = dialogView.findViewById<Button>(R.id.button_no)
            message.text = ContextCompat.getString(context, R.string.remove_from_favorites_confirmation_message)
            title.text = ContextCompat.getString(context, R.string.remove_from_favorites)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            yesButton.setOnClickListener {
                deleteItem(adapterPosition)
                dialog.hide()
            }
            noButton.setOnClickListener {
                dialog.hide()
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