package com.arnold.hilttestingapplicationartbook.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.arnold.hilttestingapplicationartbook.R
import com.arnold.hilttestingapplicationartbook.databinding.FragmentArtDetailsBinding
import com.arnold.hilttestingapplicationartbook.util.Status
import com.arnold.hilttestingapplicationartbook.viewmodel.ArtViewModel
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_art_details) {

    lateinit var viewModel: ArtViewModel

    private var fragmentBinding: FragmentArtDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.artSelectImage.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        binding.SaveButton.setOnClickListener {
            viewModel.makeArt(
                binding.artNameDetailedText.text.toString(),
                binding.authorNameDetailedText.text.toString(),
                binding.yearDetailedText.text.toString()
            )
        }
    }

    private fun subscribeToObservers(){
        viewModel.selectedImageUrl.observe(viewLifecycleOwner,Observer{ url ->
            fragmentBinding?.let {fragmentBinding ->
                glide.load(url).into(fragmentBinding.artSelectImage)
            }
        })
        viewModel.insertArtMessage.observe(viewLifecycleOwner,Observer{
           when(it.status){
               Status.SUCCESS ->{
                    Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                   findNavController().popBackStack()
                   viewModel.resetInsertArtMsg()
               }
               Status.ERROR ->{
                    Toast.makeText(requireContext(),it.message ?: "Error",
                        Toast.LENGTH_LONG).show()
               }
               Status.LOADING ->{

               }
           }
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}