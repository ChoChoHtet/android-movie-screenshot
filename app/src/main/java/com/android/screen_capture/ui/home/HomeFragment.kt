package com.android.screen_capture.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.screen_capture.databinding.FragmentHomeBinding
import com.android.screen_capture.extensions.gone
import com.android.screen_capture.extensions.visible
import com.android.screen_capture.ui.adapter.HomeAdapter
import com.android.screen_capture.ui.detail.HomeDetailFragmentDirections
import com.android.screen_capture.utils.EndlessRecyclerOnScrollListener
import com.android.screen_capture.utils.Results
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class HomeFragment :DaggerFragment() , HomeAdapter.ItemClickListener {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private val homeAdapter by lazy {
        HomeAdapter(this)
    }

    private lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        homeViewModel.getMovies(1)
        observeResponse()


    }
    private fun initAdapter(){
        val linearLayoutManager = LinearLayoutManager(requireContext())

        binding.rvMovies.apply {
            layoutManager = linearLayoutManager
            adapter = homeAdapter
            this.addOnScrollListener(object :EndlessRecyclerOnScrollListener(linearLayoutManager){
                override fun onLoadMore(page: Int, totalItemsCount: Int) {
                    Log.d("pagination","page: $page , total: ${homeViewModel.movieTotal} , count:${homeAdapter.movieItemCount}")
                    if (homeAdapter.movieItemCount < homeViewModel.movieTotal){
                        binding.pgLoading.visible()
                        //added delay time coz api was too fast
                        CoroutineScope(Dispatchers.IO).launch {
                            delay(TimeUnit.SECONDS.toMillis(2))
                            homeViewModel.getMovies(page+1)
                        }

                    }
                }

            })
        }
    }

    private fun observeResponse() {
        homeViewModel.observeMovieList.observe(viewLifecycleOwner){result->
            binding.pgLoading.gone()
            when(result){
                is Results.Loading ->{
                    Log.d("_movie","show Loading")
                    showLoading()

                }
                is Results.Success ->{
                    //Log.d("_movie","movie list: ${Gson().toJson(result.data)}")
                    Log.d("_movie","show data")
                    hideLoading()
                    homeAdapter.addMovies(result.data)
                }
                else ->{
                    Log.d("_movie","movie list error: ${(result as Results.Error).exception}")
                    showErrorView()
                }
            }
        }
    }
    private fun showErrorView(){
        binding.llMovies.gone()
        binding.errorView.visible()
        binding.errorView.setAnimation("network_error.json")
        binding.errorView.playAnimation()
    }
    private fun showLoading(){
        binding.errorView.visible()
        binding.errorView.setAnimation("loading.json")
        binding.errorView.playAnimation()
        binding.llMovies.gone()
    }
    private fun hideLoading(){
        binding.llMovies.visible()
        binding.errorView.gone()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(id: String) {
       Log.d("onItemClick","id: $id")
        val action = HomeFragmentDirections.actionHomeToDetail()
        action.imdbId = id
        findNavController().navigate(action)
    }
}