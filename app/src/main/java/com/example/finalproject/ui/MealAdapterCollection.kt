package com.example.finalproject.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.db.UsersDao
import com.example.finalproject.network.Meal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Still Trying to do it
class MealAdapterCollection(private val meals:List<Meal>, private val context:View, private val context2: Context,private val userdao:UsersDao): RecyclerView.Adapter<MealAdapterCollection.MealViewHolder>() {
    private val likeMap:MutableMap<String?,Boolean> = mutableMapOf()
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
        val sharedPreferences = context2.getSharedPreferences("key", Context.MODE_PRIVATE)
        val email=sharedPreferences.getString("Email","NoEmail")
        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .placeholder(R.drawable.baseline_downloading_24)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.mealImage)
        holder.title.text=meal.strMeal

        CoroutineScope(Dispatchers.IO).launch {
            val userData=userdao.getDataUser(email)
            val list_favourite= userData.favouriteMeals
            val x=list_favourite.find { it.idMeal==meal.idMeal }
            withContext(Dispatchers.Main) {
                if(x==null){
                    holder.heartIcon.setImageResource(R.drawable.baseline_favorite_border_24)
                }
                else{
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
                    }
                    else{
                        list_favourite.remove(meal)
                        holder.heartIcon.setImageResource(R.drawable.baseline_favorite_border_24)
                    }
                }
                userdao.updateData(userData.email,list_favourite)
            }
        }
        holder.mealImage.setOnClickListener {
            val direction = FirstFragmentDirections.actionFromHomeToDetails(meals[holder.adapterPosition])
            Navigation.findNavController(context).navigate(direction)
        }

    }

    override fun getItemCount(): Int =  meals.size
}