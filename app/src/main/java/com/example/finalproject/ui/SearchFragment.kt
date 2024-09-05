package com.example.finalproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.finalproject.R
import com.example.finalproject.adapters.SearchListAdapter
import com.example.finalproject.db.FavouritesViewModel
import com.example.finalproject.db.FavouritesViewModelFactory
import com.example.finalproject.db.LocalDataSourceImpl
import com.example.finalproject.network.APIClient
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
        val lottie = view.findViewById<LottieAnimationView>(R.id.searchLottie)
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
                        if(myAdapter.itemCount == 0){
                            lottie.visibility = View.VISIBLE
                            lottie.playAnimation()
                        }
                        else{
                            lottie.visibility = View.GONE
                        }
                        mealsList.adapter = myAdapter
                    }
                if(newText.isNullOrEmpty())
                    mealsList.visibility = View.GONE
                else
                    mealsList.visibility = View.VISIBLE

                return true
            }

        })
        mealsList.layoutManager = LinearLayoutManager(view.context)
    }
}