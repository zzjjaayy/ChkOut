package com.jay.chkout.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.jay.chkout.R
import com.jay.chkout.databinding.FragmentProductDetailsBinding
import com.jay.chkout.network.Product
import com.jay.chkout.viewModels.NetworkViewModel


class ProductDetailsFragment : Fragment() {

    private val args: ProductDetailsFragmentArgs by navArgs()

    private var _binding : FragmentProductDetailsBinding? =null
    private val binding get() = _binding!!

    private val networkViewModel : NetworkViewModel by activityViewModels()
    private lateinit var currentProduct : Product

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getCurrentProduct()
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext())
            .load(currentProduct.imageUrl)
            .into(binding.productImage)
        binding.apply {
            productTitle.text = currentProduct.productTitle
            productDesc.text = currentProduct.description
            productPrice.text = " currentProduct.price.toString()"
        }
    }

    private fun getCurrentProduct() {
        val list : List<Product>? = networkViewModel.productsList.value
        list?.let {
            for(item in list) {
                if(item.id == args.productID) {
                    currentProduct = item
                    return
                }
            }
        }
        findNavController().navigate(R.id.action_productDetailsFragment_to_productListFragment)
        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}