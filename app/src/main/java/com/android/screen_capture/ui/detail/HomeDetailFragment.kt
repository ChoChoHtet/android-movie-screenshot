package com.android.screen_capture.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.android.screen_capture.databinding.FragmentHomeDetailBinding
import com.android.screen_capture.extensions.gone
import com.android.screen_capture.extensions.visible
import com.android.screen_capture.utils.Results
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class HomeDetailFragment : DaggerFragment() {

    private var _binding: FragmentHomeDetailBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: HomeDetailViewModel

    @Inject
    lateinit var viewModelFactory:ViewModelProvider.Factory

    private val args:HomeDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[HomeDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!args.imdbId.isNullOrEmpty()){
            viewModel.getMovieByID(args.imdbId.orEmpty())
        }
        observeResponse()

    }
    private fun observeResponse() {
        viewModel.observeMovie.observe(viewLifecycleOwner){result->
            when(result){
                is Results.Loading ->{
                    Log.d("_movie","show Loading")
                    showLoading()

                }
                is Results.Success ->{
                    Log.d("_movie","show data")
                    hideLoading()
                    binding.movieDetail = result.data
                    binding.executePendingBindings()
                }
                else ->{
                    Log.d("_movie","movie list error: ${(result as Results.Error).exception}")
                    showErrorView()
                }
            }
        }
    }

    private fun showErrorView(){
        binding.gpMovieDetail.gone()
        binding.errorView.visible()
        binding.errorView.setAnimation("network_error.json")
        binding.errorView.playAnimation()
    }
    private fun showLoading(){
        binding.errorView.visible()
        binding.errorView.setAnimation("loading.json")
        binding.errorView.playAnimation()
        binding.gpMovieDetail.gone()
    }
    private fun hideLoading(){
        binding.gpMovieDetail.visible()
        binding.errorView.gone()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}