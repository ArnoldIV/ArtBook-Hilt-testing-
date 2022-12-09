package com.arnold.hilttestingapplicationartbook.view

import android.media.Image
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.arnold.hilttestingapplicationartbook.adapter.ArtRecyclerAdapter
import com.arnold.hilttestingapplicationartbook.adapter.ImageRecyclerAdapter
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val glide : RequestManager,
    private val imageRecyclerAdapter: ImageRecyclerAdapter

    ):FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when (className){
            ArtFragment::class.java.name ->ArtFragment(artRecyclerAdapter)
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }

    }
}