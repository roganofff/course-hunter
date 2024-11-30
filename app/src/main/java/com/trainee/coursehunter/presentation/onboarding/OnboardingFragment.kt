package com.trainee.coursehunter.presentation.onboarding

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.trainee.coursehunter.R
import com.trainee.coursehunter.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {
    private var _binding: FragmentOnboardingBinding? = null
    private val binding: FragmentOnboardingBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOnboardingBinding.bind(view)

        val rootView: ViewGroup = requireActivity().findViewById(android.R.id.content)
        val tagBackground = ContextCompat.getDrawable(requireContext(), R.drawable.round_bg_green)
        listOf(binding.bvWebDesign, binding.bvGoogleAnalytics, binding.bvB2BMarketing).forEach {
            it.outlineProvider = ViewOutlineProvider.BACKGROUND
            it.clipToOutline = true
            it.setupWith(rootView)
                .setFrameClearDrawable(tagBackground)
                .setBlurRadius(12f)
        }

        binding.continueButton.setOnClickListener {
            navigateToNextScreen()
        }
    }

    private fun navigateToNextScreen() {
        val action = OnboardingFragmentDirections.actionOnboardingFragmentToHomeFragment()
        findNavController().navigate(action)
    }
}