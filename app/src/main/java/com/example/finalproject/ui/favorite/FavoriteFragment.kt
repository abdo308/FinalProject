package com.example.finalproject.ui.favorite

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.FragmentSecondBinding
import com.example.finalproject.db.FavouritesViewModel
import com.example.finalproject.db.FavouritesViewModelFactory
import com.example.finalproject.db.LocalDataSourceImpl
import com.example.finalproject.network.Meal
import com.example.finalproject.repo.FavouritesRepoImpl

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FavoriteFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: FavouritesViewModel by viewModels {
        FavouritesViewModelFactory(FavouritesRepoImpl(LocalDataSourceImpl(requireContext())))
    }
    private lateinit var adapter: FavoriteAdapter
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var email: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreference = requireContext().getSharedPreferences("key", Context.MODE_PRIVATE)
        email = sharedPreference.getString("Email","")?: ""

        //Init the recycler view
        val recyclerView = binding.favoritesRV
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.itemAnimator = null
        adapter = FavoriteAdapter(this::updateFavListCallback)
        recyclerView.adapter = adapter

        subscribeToObservers()

        //Trigger getting the latest favorites list for the current user

        viewModel.getFavList(email)
    }

    private fun subscribeToObservers()
    {
        viewModel.favourites.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }

    private fun updateFavListCallback(favList: MutableList<Meal>){
        viewModel.updateFavList(email ,favList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}