package com.example.finalproject.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.db.UsersDao
import com.example.finalproject.network.Meal
import com.example.finalproject.ui.FirstFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Still Trying to do it
class MealAdapterCollection(private val meals:List<Meal>, private val context:View, private val context2: Context,private val userdao:UsersDao): RecyclerView.Adapter<MealAdapterCollection.MealViewHolder>() {
    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mealImage: ImageView = itemView.findViewById(R.id.searchField)
        var title=itemView.findViewById<TextView>(R.id.cardText)
        var heartIcon=itemView.findViewById<ImageButton>(R.id.imageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_home, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        val sharedPreferences = context2.getSharedPreferences("key", Context.MODE_PRIVATE)
        val email=sharedPreferences.getString("Email","NoEmail")
        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .placeholder(R.drawable.baseline_downloading_24)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.mealImage)
        holder.title.text=meal.strMeal

        CoroutineScope(Dispatchers.IO).launch {
            val userData = userdao.getDataUser(email)
            val list_favourite = userData.favouriteMeals
            val x = list_favourite.find { it.idMeal == meal.idMeal }
            withContext(Dispatchers.Main) {
                if (x == null) {
                    holder.heartIcon.setImageResource(R.drawable.baseline_favorite_border_24)
                } else {
                    holder.heartIcon.setImageResource(R.drawable.baseline_favorite_24)
                }
            }
        }


        holder.heartIcon.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val userData=userdao.getDataUser(email)
                val list_favourite= userData.favouriteMeals
                val x=list_favourite.find { it.idMeal==meal.idMeal }
                withContext(Dispatchers.Main){
                    if(x==null){
                        list_favourite.add(meal)
                        holder.heartIcon.setImageResource(R.drawable.baseline_favorite_24)
                        Toast.makeText(context2,"Added in Favourites",Toast.LENGTH_SHORT).show()
                        userdao.updateData(userData.email, list_favourite)

                    }
                    else{
                        showFavConfirmationMessage(context2) {
                            list_favourite.remove(meal)
                            holder.heartIcon.setImageResource(R.drawable.baseline_favorite_border_24)
                            Toast.makeText(context2, "Removed From Favourites", Toast.LENGTH_SHORT)
                                .show()
                            CoroutineScope(Dispatchers.IO).launch {
                                userdao.updateData(userData.email, list_favourite)
                            }

                        }
                    }
                }
            }
        }
        holder.mealImage.setOnClickListener {
            val direction = FirstFragmentDirections.actionFromHomeToDetails(meals[holder.adapterPosition])
            Navigation.findNavController(context).navigate(direction)
        }

    }
    private fun showFavConfirmationMessage(context: Context, onConfirm: () -> Unit) {
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
            dialog.hide()
            onConfirm()
        }
        noButton.setOnClickListener {
            dialog.hide()
        }
    }
    override fun getItemCount(): Int =  meals.size
}