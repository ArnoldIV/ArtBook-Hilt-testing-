package com.arnold.hilttestingapplicationartbook.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arnold.hilttestingapplicationartbook.model.ImageResponse
import com.arnold.hilttestingapplicationartbook.roomdb.ArtEntity
import com.arnold.hilttestingapplicationartbook.util.Resource

class FakeArtRepository : ArtRepositoryInterface {

    private val arts = mutableListOf<ArtEntity>()
    private val artsLiveData = MutableLiveData<List<ArtEntity>>(arts)

    override suspend fun insterArt(art: ArtEntity) {
        arts.add(art)
    }

    override suspend fun deleteArt(art: ArtEntity) {
        arts.remove(art)
    }

    override fun getArt(): LiveData<List<ArtEntity>> {
        return artsLiveData
    }

    override suspend fun searchImage(imgaeString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }
    private fun refreshLiveData() {
        artsLiveData.postValue(arts)
    }
}