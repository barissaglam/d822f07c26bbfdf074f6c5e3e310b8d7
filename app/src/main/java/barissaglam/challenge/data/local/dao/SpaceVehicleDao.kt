package barissaglam.challenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import barissaglam.challenge.data.local.model.SpaceVehicleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SpaceVehicleDao {
    @Insert
    suspend fun defineSpaceVehicle(spaceVehicleEntity: SpaceVehicleEntity)

    @Query("SELECT * FROM space_vehicle ORDER BY id DESC")
    fun getSpaceVehicles(): Flow<List<SpaceVehicleEntity>>
}