package barissaglam.challenge.data.remote

import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import barissaglam.challenge.base.extension.orZero
import barissaglam.challenge.data.remote.model.SpaceStation
import barissaglam.challenge.data.uimodel.SpaceStationUIModel
import barissaglam.challenge.util.Resource
import barissaglam.challenge.util.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(private val apiService: RemoteApiService) {

    fun getSpaceStations(): Flow<Resource<List<SpaceStationUIModel>>> = flow {
        val responseCall = apiService.getSpaceStations().map {
            SpaceStationUIModel(
                name = ObservableField(it.name),
                coordinateX = it.coordinateX.orZero(),
                coordinateY = it.coordinateY.orZero(),
                capacity = ObservableInt(it.capacity ?: 0),
                stock = ObservableInt(it.stock ?: 0),
                need = ObservableInt(it.need ?: 0)
            )
        }
        emit(Resource.Success(responseCall))
    }.execute()


    private fun <T> Flow<Resource<T>>.execute(): Flow<Resource<T>> =
        onStart { emit(Resource.Loading) }
            .catch { throwable -> emit(Resource.Error(throwable)) }
            .flowOn(Dispatchers.IO)
}