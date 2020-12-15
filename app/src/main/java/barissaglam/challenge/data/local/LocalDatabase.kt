package barissaglam.challenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import barissaglam.challenge.data.local.dao.FavoriteStationDao
import barissaglam.challenge.data.local.dao.SpaceVehicleDao
import barissaglam.challenge.data.local.model.FavoriteStationEntity
import barissaglam.challenge.data.local.model.SpaceVehicleEntity

@Database(entities = [SpaceVehicleEntity::class,FavoriteStationEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getSpaceVehicleDao(): SpaceVehicleDao
    abstract fun getFavoriteStationDao(): FavoriteStationDao
}