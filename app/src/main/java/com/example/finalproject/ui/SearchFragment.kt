package com.example.finalproject.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.adapters.SearchListAdapter
import com.example.finalproject.db.FavouritesViewModel
import com.example.finalproject.db.FavouritesViewModelFactory
import com.example.finalproject.db.LocalDataSourceImpl
import com.example.finalproject.models.UserFavourites
import com.example.finalproject.network.APIClient
import com.example.finalproject.network.Meal
import com.example.finalproject.network.RetrofitViewModel
import com.example.finalproject.network.ViewModelFactory
import com.example.finalproject.repo.FavouritesRepoImpl
import com.example.finalproject.repo.MealRepoImpl


class SearchFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mealsList  = view.findViewById<RecyclerView>(R.id.meals_list)
        val searchView = view.findViewById<SearchView>(R.id.searchField)
        val viewModelFactory = ViewModelFactory(mealRepo = MealRepoImpl(APIClient))
        val favViewModelFactory = FavouritesViewModelFactory(favouritesRepo = FavouritesRepoImpl(LocalDataSourceImpl(requireContext())))
        val favViewModel = ViewModelProvider(this,favViewModelFactory).get(FavouritesViewModel::class.java)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(RetrofitViewModel::class.java)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var  myAdapter : SearchListAdapter
                viewModel.getMealsListBySearch(newText.toString())
                    viewModel.mealsListBySearch.observe(viewLifecycleOwner) { list ->
                        myAdapter =
                            SearchListAdapter(list, requireView(), requireContext(),
                                favViewModel,viewLifecycleOwner
                            )
                        mealsList.adapter = myAdapter
                    }

                return true
            }

        })
        mealsList.layoutManager = LinearLayoutManager(view.context)
    }
}