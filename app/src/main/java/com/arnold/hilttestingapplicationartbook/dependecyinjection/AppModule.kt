package com.arnold.hilttestingapplicationartbook.dependecyinjection

import android.content.Context
import androidx.room.Room
import com.arnold.hilttestingapplicationartbook.R
import com.arnold.hilttestingapplicationartbook.api.RetrofitAPI
import com.arnold.hilttestingapplicationartbook.repo.ArtRepository
import com.arnold.hilttestingapplicationartbook.repo.ArtRepositoryInterface
import com.arnold.hilttestingapplicationartbook.roomdb.ArtDao
import com.arnold.hilttestingapplicationartbook.roomdb.ArtDataBase
import com.arnold.hilttestingapplicationartbook.util.Util.BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, ArtDataBase::class.java, "ArtBookDB"
    ).build()

    @Singleton
    @Provides
    fun injectDao(dataBase: ArtDataBase) = dataBase.artDao()

    @Singleton
    @Provides
    fun injectRetrofitAPI(): RetrofitAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectTrueRepo(dao: ArtDao, api: RetrofitAPI) = ArtRepository(dao, api)
            as ArtRepositoryInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)

        )

}