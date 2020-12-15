package barissaglam.challenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import barissaglam.challenge.data.local.model.FavoriteStationEntity
import barissaglam.challenge.data.local.model.SpaceVehicleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteStationDao {
    @Insert
    suspend fun addStationToFavorite(favoriteStationEntity: FavoriteStationEntity)

    @Query("DELETE FROM favorite_station WHERE id=:id")
    suspend fun deleteStationFromFavorite(id: Int)

    @Query("SELECT * FROM favorite_station ORDER BY id DESC")
    fun getFavoriteStations(): Flow<List<FavoriteStationEntity>>
}