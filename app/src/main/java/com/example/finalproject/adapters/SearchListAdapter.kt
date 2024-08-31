package com.example.finalproject.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.db.FavouritesViewModel
import com.example.finalproject.models.UserFavourites
import com.example.finalproject.network.Meal
import com.example.finalproject.ui.SearchFragmentDirections

class SearchListAdapter(
    private val meals:List<Meal>,
    private val view: View,
    private val context: Context,
    private val favViewModel : FavouritesViewModel,
    private val owner : LifecycleOwner
    ): RecyclerView.Adapter<SearchListAdapter.SearchItemViewHolder>() {
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
            favViewModel.addUser(UserFavourites(email ?: "", emptyList<Meal>().toMutableList()))
            favViewModel.getFavList(email ?: "")
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
            val isLiked = favs.find { id == it.idMeal }
            if (isLiked == null) {
                holder.heartIcon.setImageResource(R.drawable.baseline_favorite_border_24)
                liked = false
            } else {
                holder.heartIcon.setImageResource(R.drawable.baseline_favorite_24)
                liked = true
            }
            holder.heartIcon.setOnClickListener {
                if (liked) {
                 liked = showFavConfirmationMessage(holder,favs,meal,email ?: "")
                } else {
                    holder.heartIcon.setImageResource(R.drawable.baseline_favorite_24)
                    favs.add(meal)
                    Toast.makeText(context, "Added to favourites", Toast.LENGTH_SHORT).show()
                    liked = true
                    favViewModel.updateFavList(email ?: "", favs)
                }

            }

        }

    }

    override fun getItemCount(): Int =  meals.size

    private fun showFavConfirmationMessage(
        holder: SearchItemViewHolder,
        favs : MutableList<Meal>,
        meal: Meal,
        email : String) : Boolean
    {
        val dialogView = LayoutInflater.from(context)
            .inflate(R.layout.confirmation_message_view, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(dialogView)
        val dialog = builder.create()
        val message = dialogView.findViewById<TextView>(R.id.messageText)
        val title = dialogView.findViewById<TextView>(R.id.titleText)
        val yesButton = dialogView.findViewById<Button>(R.id.button_yes)
        val noButton = dialogView.findViewById<Button>(R.id.button_no)
        var liked = true
        message.text = "Are you sure you want to remove this item from favourites?"
        title.text = "Remove from favourites"
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        yesButton.setOnClickListener {
            holder.heartIcon.setImageResource(R.drawable.baseline_favorite_border_24)
            favs.remove(meal)
            favViewModel.updateFavList(email, favs)
            liked = false
            dialog.hide()
        }
        noButton.setOnClickListener {
            dialog.hide()
        }
        return liked
    }
}