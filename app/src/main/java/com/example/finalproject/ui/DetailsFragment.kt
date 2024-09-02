package com.example.finalproject.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.adapters.SearchListAdapter.SearchItemViewHolder
import com.example.finalproject.db.FavouritesViewModel
import com.example.finalproject.db.FavouritesViewModelFactory
import com.example.finalproject.db.LocalDataSourceImpl
import com.example.finalproject.network.Meal
import com.example.finalproject.repo.FavouritesRepoImpl
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
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
        val favViewModelFactory = FavouritesViewModelFactory(
            FavouritesRepoImpl(
                LocalDataSourceImpl(
                    requireContext()
                )
            )
        )
        val favViewModel = ViewModelProvider(this,favViewModelFactory)[FavouritesViewModel::class.java]
        val meal: Meal = arguments?.let { DetailsFragmentArgs.fromBundle(it).meal } as Meal
        val showMoreTextView: TextView = view.findViewById(R.id.show_more_text_view)
        val showLessTextView: TextView = view.findViewById(R.id.show_less_text_view)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val showMoreBtn: Button = view.findViewById(R.id.show_more_btn)
        val showLessBtn: Button = view.findViewById(R.id.show_less_btn)
        val showMoreView = view.findViewById<View>(R.id.show_more_view)
        val showLessView = view.findViewById<View>(R.id.show_less_view)
        val videoPlayerView: YouTubePlayerView = view.findViewById(R.id.video_player_view)
        lifecycle.addObserver(videoPlayerView)
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
            view.findViewById<TextView>(R.id.category_name).text = meal.strCategory
            videoPlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = Uri.parse(meal.strYoutube)?.getQueryParameter("v") ?: "XIMLoLxmTDw"
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            })
        }
        val sharedPreferences = view.context.getSharedPreferences("key", Context.MODE_PRIVATE)
        val email=sharedPreferences.getString("Email", "No email found") ?: ""
        var inFav = false
        var favourites : MutableList<Meal> = emptyList<Meal>().toMutableList()
        favViewModel.getFavList(email)
        favViewModel.favourites.observe(viewLifecycleOwner){ favour ->
            favourites = favour
            inFav = if(favour.find{it.idMeal == meal.idMeal} != null){
                view.findViewById<ImageView>(R.id.fav_icon22).setImageResource(R.drawable.baseline_favorite_24)
                true
            }
            else {
                view.findViewById<ImageView>(R.id.fav_icon22).setImageResource(R.drawable.baseline_favorite_border_24)
                false
            }
        }
        val favButton: ImageButton = view.findViewById(R.id.fav_icon22)
        favButton.setOnClickListener {
             if (inFav) {
                 showFavConfirmationMessage(favButton, favourites, meal, email, favViewModel)
            } else {
                view.findViewById<ImageView>(R.id.fav_icon22)
                    .setImageResource(R.drawable.baseline_favorite_24)
                favourites.add(meal)
                 favViewModel.updateFavList(email, favourites)
                 Toast.makeText(context, "Added to favourites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showFavConfirmationMessage(
        favIcon: ImageView,
        favs : MutableList<Meal>,
        meal: Meal,
        email: String,
        vm: FavouritesViewModel)
    {
        val dialogView = LayoutInflater.from(context)
            .inflate(R.layout.confirmation_message_view, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        val dialog = builder.create()
        val message = dialogView.findViewById<TextView>(R.id.messageText)
        val title = dialogView.findViewById<TextView>(R.id.titleText)
        val yesButton = dialogView.findViewById<Button>(R.id.button_yes)
        val noButton = dialogView.findViewById<Button>(R.id.button_no)
        message.text = "Are you sure you want to remove this item from favourites?"
        title.text = "Remove from favourites"
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        yesButton.setOnClickListener {
            favIcon.setImageResource(R.drawable.baseline_favorite_border_24)
            favs.remove(meal)
            dialog.hide()
            vm.updateFavList(email, favs)
        }
        noButton.setOnClickListener {
            dialog.hide()
        }
    }
}