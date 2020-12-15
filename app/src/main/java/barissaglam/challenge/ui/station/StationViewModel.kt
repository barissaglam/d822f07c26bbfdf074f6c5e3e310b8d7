package barissaglam.challenge.ui.station

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import barissaglam.challenge.base.extension.orZero
import barissaglam.challenge.base.viewmodel.BaseViewModel
import barissaglam.challenge.data.local.LocalRepository
import barissaglam.challenge.data.remote.RemoteRepository
import barissaglam.challenge.data.remote.model.SpaceStation
import barissaglam.challenge.data.uimodel.SpaceStationUIModel
import barissaglam.challenge.data.uimodel.SpaceVehicleUIModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

class StationViewModel @ViewModelInject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : BaseViewModel() {
    val stateLiveData = MutableLiveData<State>()
    val spaceStationList = ArrayList<SpaceStationUIModel>()
    val spaceVehicle = ObservableField<SpaceVehicleUIModel>()
    val currentSpaceStation = ObservableField<SpaceStationUIModel>()

    var isGameFinished = false

    init {
        getSpaceVehicleInfo()
    }

    fun getSpaceStations() {
        remoteRepository.getSpaceStations().sendRequest {
            spaceStationList.addAll(it)
            stateLiveData.value = State.OnSpaceStationsLoaded
            if (it.isNotEmpty()) this.currentSpaceStation.set(it[0])
        }
    }

    private fun getSpaceVehicleInfo() {
        localRepository.getSpaceVehicles().sendLocalRequest {
            if (it.isNotEmpty()) this.spaceVehicle.set(it[0])
        }
    }

    fun addFavoriteStation(spaceStationUIModel: SpaceStationUIModel) {
        viewModelScope.launch {
            localRepository.addStationToFavorite(spaceStationUIModel)
            spaceStationUIModel.isFavorite.set(true)
        }
    }

    fun updateStationsDistance() {
        for (i in spaceStationList.indices) {
            val station = spaceStationList[i]

            station.distanceToWorld =
                DecimalFormat("#.##").format(
                    sqrt(
                        (
                                (station.coordinateX.orZero() - currentSpaceStation.get()?.coordinateX.orZero()).pow(2.0)) +
                                (station.coordinateY.orZero() - currentSpaceStation.get()?.coordinateY.orZero()).pow(2.0)
                    )
                ).toDouble()
        }
    }

    fun getTotalRemainingEUS(): Int {
        var total = 0
        for (spaceStation in spaceStationList) if (!spaceStation.isVisited.get()) total += spaceStation.distanceToWorld.roundToInt()
        return total
    }

    fun getSearchedItemPosition(name: String): Int {
        spaceStationList.forEachIndexed { index, spaceStationUIModel ->
            if (name.contains(spaceStationUIModel.name.get().orEmpty())) return index
        }
        return -1
    }


    sealed class State {
        object OnSpaceStationsLoaded : State()
    }
}