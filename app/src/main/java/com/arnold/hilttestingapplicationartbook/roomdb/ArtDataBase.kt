package com.arnold.hilttestingapplicationartbook.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArtEntity::class], version = 1)
abstract class ArtDataBase : RoomDatabase(){
    abstract fun artDao() : ArtDao
}