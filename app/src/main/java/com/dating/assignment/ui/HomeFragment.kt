package com.dating.assignment.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dating.assignment.R
import com.dating.assignment.adapter.HomeAdapter
import com.dating.assignment.databinding.FragmentHomeBinding
import com.dating.assignment.model.Article
import com.dating.assignment.util.Resource
import com.dating.assignment.vm.AssignmentViewModel
import dagger.hilt.android.AndroidEntryPoint

// Annotating the Fragment with @AndroidEntryPoint for Dagger Hilt integration
@AndroidEntryPoint
class HomeFragment : Fragment() {

    // data binding for the fragment layout
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // ViewModel instance provided by Dagger Hilt
    private val viewModel by viewModels<AssignmentViewModel>()

    // Adapter for the RecyclerView
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using data binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setUpRecyclerView()

        // Observe the data from the ViewModel
        viewModel.dummyDataResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    // Show shimmer effect while data is loading
                    loadingShimmer()
                }

                is Resource.Success -> {
                    // Hide shimmer effect and update RecyclerView with the loaded data
                    notLoadingShimmer()
                    response.data?.let { data ->
                        homeAdapter.diffCallBack.submitList(data.articles)
                        Log.e("Home", data.toString())
                    }
                }

                is Resource.Error -> Toast.makeText(
                    requireContext(), response.message.toString(), Toast.LENGTH_SHORT
                ).show()

                else -> {
                    // Handle other scenarios as needed
                    Toast.makeText(requireContext(), "Retry", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Set item click listener for the RecyclerView items
        homeAdapter.setOnItemClickListener(object : HomeAdapter.OnItemClickListener {
            override fun onItemClick(article: Article) {
                // Navigate to the ArticleFragment when an item is clicked
                val action = HomeFragmentDirections.actionHomeFragmentToArticleFragment(article.url.toString())
                findNavController().navigate(action)
            }
        })

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()

        // Release the binding when the fragment is destroyed
        _binding = null
    }

    // Set up the RecyclerView and its adapter
    private fun setUpRecyclerView() {
        homeAdapter = HomeAdapter()
        binding.rvHome.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadingShimmer() {
        binding.shimmerLayout.startShimmer()
        binding.shimmerLayout.visibility = View.VISIBLE
        binding.rvHome.visibility = View.GONE
    }

    private fun notLoadingShimmer() {
        binding.shimmerLayout.stopShimmer()
        binding.shimmerLayout.visibility = View.GONE
        binding.rvHome.visibility = View.VISIBLE
    }
}
