package com.example.finalproject.ui
import MealAdapter
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.recursiveFetchArrayMap
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentFirstBinding
import com.example.finalproject.db.ApplicationDataBase
import com.example.finalproject.network.APIClient
import com.example.finalproject.network.RemoteDataSource
import com.example.finalproject.network.RetrofitViewModel
import com.example.finalproject.network.ViewModelFactory
import com.example.finalproject.repo.MealRepoImpl
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var navController:NavController
    private val mealRepo = MealRepoImpl(APIClient)
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
        Log.d("msg","Created")
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val database = ApplicationDataBase.getInstance(requireContext())
        val userDao = database.userDao()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.meal.observe(viewLifecycleOwner) { meals ->
            val mealAdapter = MealAdapter(meals,requireView(), requireContext(),userDao)
            recyclerView.adapter = mealAdapter
        }
        viewModel.fetchRandom()

        val recyclerView2 = view.findViewById<RecyclerView>(R.id.recyclerView2)
        recyclerView2.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        val itemSpacing = 50
        recyclerView2.addItemDecoration(ItemSpacingDecoration(itemSpacing))
        viewModel.mealCollection.observe(viewLifecycleOwner){meals->
            val mealAdapter=MealAdapterCollection(meals,requireView(),requireContext(),userDao)
            recyclerView2.adapter=mealAdapter
        }
        viewModel.fetchRandomCollection()

    }

    override fun onDestroyView() {
        Log.d("msg","destroy")

        super.onDestroyView()
        _binding = null
    }

    class ItemSpacingDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.right = space
        }
    }
}