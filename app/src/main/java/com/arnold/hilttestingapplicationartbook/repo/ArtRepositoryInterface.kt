package com.arnold.hilttestingapplicationartbook.repo

import androidx.lifecycle.LiveData
import com.arnold.hilttestingapplicationartbook.model.ImageResponse
import com.arnold.hilttestingapplicationartbook.roomdb.ArtEntity
import com.arnold.hilttestingapplicationartbook.util.Resource

interface ArtRepositoryInterface {

    suspend fun insterArt(art: ArtEntity)

    suspend fun deleteArt(art: ArtEntity)

    fun getArt() : LiveData<List<ArtEntity>>

    suspend fun  searchImage(imgaeString: String): Resource<ImageResponse>
}