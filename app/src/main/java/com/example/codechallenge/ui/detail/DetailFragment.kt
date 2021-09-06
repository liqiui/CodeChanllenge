package com.example.codechallenge.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.codechallenge.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val show = DetailFragmentArgs.fromBundle(requireArguments()).show
        val viewModelFactory = DetailViewModel.Factory( show,application)
        binding.viewModel = ViewModelProvider(
            this,viewModelFactory).get(DetailViewModel::class.java)
        return  binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}