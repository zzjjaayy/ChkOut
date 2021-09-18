package com.jay.chkout.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.jay.chkout.R
import com.jay.chkout.databinding.FragmentProductListBinding
import com.jay.chkout.viewModels.ApiStatus
import com.jay.chkout.viewModels.NetworkViewModel

class ProductListFragment : Fragment() {

    private var _binding : FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val networkViewModel : NetworkViewModel by activityViewModels()

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        firebaseAuth = FirebaseAuth.getInstance()
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
        setUserData()

        networkViewModel.productsList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        changeIndicatorVisibility()

        setHasOptionsMenu(true)
    }

    private fun setUserData() {
        if(firebaseAuth.currentUser != null) {
            val name = firebaseAuth.currentUser?.displayName?.substringBefore(" ")
            name?.let{
                binding.welcomeText.text =
                    getString(R.string.welcome_user, name)
            }
            val imgUrl = firebaseAuth.currentUser?.photoUrl
            Glide.with(requireContext()).load(imgUrl).circleCrop()
                .into(binding.profileImage)
        } else {
            binding.welcomeText.text = getString(R.string.welcome_user, "Guest")
            binding.profileImage.setImageResource(R.drawable.ic_account_circle_24)
        }
    }

    private fun changeIndicatorVisibility() {
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.sign_out) {
            if(firebaseAuth.currentUser == null) {
                findNavController().navigate(R.id.action_productListFragment_to_loginFragment)
            } else signOut()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun signOut() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            firebaseAuth.signOut()
            findNavController().navigate(R.id.action_productListFragment_to_loginFragment)
        }
        alertDialogBuilder.setNegativeButton("No") {_,_ -> } // Nothing should happen
        alertDialogBuilder.setTitle("Log Out?")
        alertDialogBuilder.setMessage("Are you sure you want to Log out?")
        alertDialogBuilder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}