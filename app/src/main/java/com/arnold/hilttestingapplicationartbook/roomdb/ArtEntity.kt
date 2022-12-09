package com.arnold.hilttestingapplicationartbook.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class ArtEntity (
    var name: String,
    var ArtistName: String,
    var year: Int,
    var imageUrl:String,
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null)
