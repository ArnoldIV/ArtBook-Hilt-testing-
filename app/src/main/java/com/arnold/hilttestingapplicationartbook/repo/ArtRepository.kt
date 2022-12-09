package com.arnold.hilttestingapplicationartbook.repo

import androidx.lifecycle.LiveData
import com.arnold.hilttestingapplicationartbook.api.RetrofitAPI
import com.arnold.hilttestingapplicationartbook.model.ImageResponse
import com.arnold.hilttestingapplicationartbook.roomdb.ArtDao
import com.arnold.hilttestingapplicationartbook.roomdb.ArtEntity
import com.arnold.hilttestingapplicationartbook.util.Resource
import java.lang.Exception
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI) : ArtRepositoryInterface {
    override suspend fun insterArt(art: ArtEntity) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: ArtEntity) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<ArtEntity>> {
        return  artDao.observeArts()
    }

    override suspend fun searchImage(imgaeString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imgaeString)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            } else {
                Resource.error("Error",null)
            }
        } catch (e: Exception) {
            Resource.error("No data!",null)
        }
    }
}