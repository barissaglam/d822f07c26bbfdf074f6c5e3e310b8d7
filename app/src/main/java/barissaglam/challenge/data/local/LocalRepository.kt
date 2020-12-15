package barissaglam.challenge.data.local

import barissaglam.challenge.data.local.dao.FavoriteStationDao
import barissaglam.challenge.data.local.dao.SpaceVehicleDao
import barissaglam.challenge.data.local.model.FavoriteStationEntity
import barissaglam.challenge.data.local.model.SpaceVehicleEntity
import barissaglam.challenge.data.uimodel.FavoriteStationUIModel
import barissaglam.challenge.data.uimodel.SpaceStationUIModel
import barissaglam.challenge.data.uimodel.SpaceVehicleUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val spaceVehicleDao: SpaceVehicleDao,
    private val favoriteStationDao: FavoriteStationDao
) {
    suspend fun defineSpaceVehicle(spaceVehicleEntity: SpaceVehicleEntity) {
        spaceVehicleDao.defineSpaceVehicle(spaceVehicleEntity)
    }

    /*
        Vehicle
     */
    fun getSpaceVehicles(): Flow<List<SpaceVehicleUIModel>> {
        return spaceVehicleDao.getSpaceVehicles().map {
            it.map { entity ->
                SpaceVehicleUIModel(
                    id = entity.id,
                    vehicleName = entity.vehicleName,
                    durability = entity.durability,
                    speed = entity.speed,
                    capacity = entity.capacity,
                    damageCapacity = entity.damageCapacity
                )
            }
        }.flowOn(Dispatchers.IO)
    }

    /*
        Favorite
     */
    fun getFavoriteStations(): Flow<List<FavoriteStationUIModel>> {
        return favoriteStationDao.getFavoriteStations().map {
            it.map { entity ->
                FavoriteStationUIModel(
                    id = entity.id,
                    name = entity.stationName,
                    coordinateX = entity.coordinateX,
                    coordinateY = entity.coordinateY
                )
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun addStationToFavorite(spaceStationUIModel: SpaceStationUIModel) {
        favoriteStationDao.addStationToFavorite(
            FavoriteStationEntity(
                stationName = spaceStationUIModel.name.get().orEmpty(),
                coordinateX = spaceStationUIModel.coordinateX,
                coordinateY = spaceStationUIModel.coordinateY
            )
        )
    }

    suspend fun deleteStationFromFavorite(id: Int) {
        return favoriteStationDao.deleteStationFromFavorite(id)
    }

}