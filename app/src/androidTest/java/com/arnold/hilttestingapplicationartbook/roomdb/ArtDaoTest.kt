package com.arnold.hilttestingapplicationartbook.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Database
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.arnold.hilttestingapplicationartbook.getOrAwaitValueTest
import com.arnold.hilttestingapplicationartbook.roomdb.ArtDao
import com.arnold.hilttestingapplicationartbook.roomdb.ArtDataBase
import com.arnold.hilttestingapplicationartbook.roomdb.ArtEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: ArtDataBase

    private lateinit var dao : ArtDao

    @Before
    fun setup(){
//        database = Room.inMemoryDatabaseBuilder(
//            ApplicationProvider.getApplicationContext(), ArtDataBase::class.java
//        ).allowMainThreadQueries().build()

        dao = database.artDao()

    }
    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertArtTesting() = runTest {
        val exampleArt = ArtEntity("Mona Lisa", "Da Vicni", 1700,"test.com",1)
        dao.insertArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValueTest()
        assertThat(list).contains(exampleArt)

    }

    @Test
    fun deleteArtTesting() = runTest {
        val exampleArt = ArtEntity("Mona Lisa", "Da Vicni", 1700,"test.com",1)
        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)


        val list = dao.observeArts().getOrAwaitValueTest()
        assertThat(list).doesNotContain(exampleArt)
    }


}