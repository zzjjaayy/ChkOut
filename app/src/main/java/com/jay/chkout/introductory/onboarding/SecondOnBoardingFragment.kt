package com.jay.chkout.introductory.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.jay.chkout.R

class SecondOnBoardingFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.fragment_second_on_boarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            findViewById<ExtendedFloatingActionButton>(R.id.next_fab).setOnClickListener {
                onBoardingFinished()
                if(firebaseAuth.currentUser == null) {
                    findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
                } else findNavController().navigate(R.id.action_viewPagerFragment_to_productListFragment)
            }
        }
    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}