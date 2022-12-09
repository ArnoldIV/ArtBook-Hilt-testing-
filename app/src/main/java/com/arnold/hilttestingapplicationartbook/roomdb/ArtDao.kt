package com.arnold.hilttestingapplicationartbook.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art: ArtEntity)

    @Delete
    suspend fun deleteArt(art: ArtEntity)

    @Query("SELECT * FROM arts")
    fun observeArts(): LiveData<List<ArtEntity>>

}