package com.jay.chkout.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jay.chkout.databinding.FragmentProductListBinding
import com.jay.chkout.viewModels.ApiStatus
import com.jay.chkout.viewModels.NetworkViewModel

class ProductListFragment : Fragment() {

    private var _binding : FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val networkViewModel : NetworkViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        // Inflate the layout for this fragment
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ProductAdapter{
            val action = ProductListFragmentDirections
                .actionProductListFragmentToProductDetailsFragment(it.id)
            findNavController().navigate(action)
        }
        binding.apply {
            viewModel = networkViewModel
            lifecycleOwner = this@ProductListFragment
            productsRecyclerView.adapter = adapter
        }
        networkViewModel.productsList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        networkViewModel.status.observe(viewLifecycleOwner, {
            when(it){
                ApiStatus.LOADING -> {
                    binding.welcomeText.visibility = View.VISIBLE
                    binding.loadingIndicator.visibility = View.VISIBLE
                    binding.errorIndicator.visibility = View.GONE
                    binding.productsRecyclerView.visibility = View.GONE
                }
                ApiStatus.DONE -> {
                    binding.welcomeText.visibility = View.VISIBLE
                    binding.loadingIndicator.visibility = View.GONE
                    binding.errorIndicator.visibility = View.GONE
                    binding.productsRecyclerView.visibility = View.VISIBLE
                }
                ApiStatus.ERROR -> {
                    binding.welcomeText.visibility = View.GONE
                    binding.loadingIndicator.visibility = View.GONE
                    binding.errorIndicator.visibility = View.VISIBLE
                    binding.productsRecyclerView.visibility = View.GONE
                }
                else -> {}
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}