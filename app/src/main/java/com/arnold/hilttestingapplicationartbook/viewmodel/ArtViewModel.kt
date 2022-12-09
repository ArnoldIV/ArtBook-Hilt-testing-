package com.arnold.hilttestingapplicationartbook.viewmodel


import androidx.lifecycle.*
import com.arnold.hilttestingapplicationartbook.model.ImageResponse
import com.arnold.hilttestingapplicationartbook.repo.ArtRepositoryInterface
import com.arnold.hilttestingapplicationartbook.roomdb.ArtEntity
import com.arnold.hilttestingapplicationartbook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository: ArtRepositoryInterface
) : ViewModel(), LifecycleObserver {

    //Art Fragment

    val artList = repository.getArt()
    //Image API Fragment

    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage

    //Art Details Fragment

    private var insertArtMsg = MutableLiveData<Resource<ArtEntity>>()
    val insertArtMessage: LiveData<Resource<ArtEntity>>
        get() = insertArtMsg

    fun resetInsertArtMsg(){
        insertArtMsg = MutableLiveData<Resource<ArtEntity>>()
    }

    fun setSelectedImage(url: String){
        selectedImage.postValue(url)
    }

    fun deleteArt(art: ArtEntity) = viewModelScope.launch{
        repository.deleteArt(art)
    }

    private fun insertArt(art:ArtEntity) = viewModelScope.launch {
        repository.insterArt(art)
    }

    fun makeArt(name: String, artistName: String, year: String){
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()){
            insertArtMsg.postValue(Resource.error("Enter name, artist, year",null))
            return
        }
        val yearInt = try {
            year.toInt()
        } catch (e: java.lang.Exception){
            insertArtMsg.postValue(Resource.error("Year should be number",null))
            return
        }
        val art = ArtEntity(name,artistName,yearInt, selectedImage.value?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }

    fun searchForImage(searchString: String) {
        if (searchString.isEmpty()) {
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }
    }

}