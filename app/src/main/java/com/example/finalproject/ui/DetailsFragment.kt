package com.example.finalproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.network.Meal
import kotlinx.coroutines.launch


class DetailsFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val meal: Meal = arguments?.let { DetailsFragmentArgs.fromBundle(it).meal } as Meal
        val showMoreTextView: TextView = view.findViewById(R.id.show_more_text_view)
        val showLessTextView: TextView = view.findViewById(R.id.show_less_text_view)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val showMoreBtn: Button = view.findViewById(R.id.show_more_btn)
        val showLessBtn: Button = view.findViewById(R.id.show_less_btn)
        val showMoreView = view.findViewById<View>(R.id.show_more_view)
        val showLessView = view.findViewById<View>(R.id.show_less_view)
        showMoreBtn.setOnClickListener{
            showMoreView.visibility = View.VISIBLE
            showLessView.visibility = View.GONE
        }
        showLessBtn.setOnClickListener{
            showMoreView.visibility = View.GONE
            showLessView.visibility = View.VISIBLE

        }
        lifecycleScope.launch {
            Glide.with(imageView)
                .load(meal.strMealThumb)
                .override(imageView.width, imageView.height)
                .into(imageView)
            showLessTextView.text = meal.strInstructions?.substring(0, (20 * meal.strInstructions.length) / 100)
            showMoreTextView.text = meal.strInstructions
            view.findViewById<TextView>(R.id.meal_name).text = meal.strMeal
        }
    }
}