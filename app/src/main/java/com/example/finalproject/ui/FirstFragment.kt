package com.example.finalproject.ui

import MealAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.recursiveFetchArrayMap
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentFirstBinding
import com.example.finalproject.network.APIClient
import com.example.finalproject.network.RemoteDataSource
import com.example.finalproject.network.RetrofitViewModel
import com.example.finalproject.network.ViewModelFactory
import com.example.finalproject.repo.MealRepoImpl

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

private var _binding: FragmentFirstBinding? = null
    private val mealRepo = MealRepoImpl(APIClient) // Provide the concrete implementation
    private val viewModel: RetrofitViewModel by viewModels {
        ViewModelFactory(mealRepo)
    }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

      _binding = FragmentFirstBinding.inflate(inflater, container, false)

      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.meal.observe(viewLifecycleOwner) { meals ->
            val mealAdapter = MealAdapter(meals,requireView())
            recyclerView.adapter = mealAdapter
        }
        viewModel.fetchRandom()

    }

    override fun onResume() {
        super.onResume()
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        viewModel.meal.observe(viewLifecycleOwner) { meals ->
            val mealAdapter = MealAdapter(meals,requireView())
            recyclerView?.adapter = mealAdapter
        }
        viewModel.fetchRandom()
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}