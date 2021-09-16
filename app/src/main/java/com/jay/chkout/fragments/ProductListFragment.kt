package com.jay.chkout.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jay.chkout.databinding.FragmentProductListBinding
import com.jay.chkout.viewModels.NetworkViewModel

class ProductListFragment : Fragment() {

    private var _binding : FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val networkViewModel : NetworkViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ProductAdapter()
        binding.apply {
            viewModel = networkViewModel
            lifecycleOwner = this@ProductListFragment
            productsRecyclerView.adapter = adapter
        }
        networkViewModel.productsList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}