package barissaglam.challenge.ui.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import barissaglam.challenge.base.viewmodel.BaseViewModel
import barissaglam.challenge.data.local.LocalRepository
import barissaglam.challenge.data.uimodel.FavoriteStationUIModel
import kotlinx.coroutines.launch

class FavoriteViewModel @ViewModelInject constructor(
    private val localRepository: LocalRepository
) : BaseViewModel() {
    val stateLiveData = MutableLiveData<State>()
    val favoriteList = ArrayList<FavoriteStationUIModel>()

    init {
        getFavoriteStations()
    }

    private fun getFavoriteStations() {
        localRepository.getFavoriteStations().sendLocalRequest {
            favoriteList.clear()
            favoriteList.addAll(it)
            stateLiveData.value = State.OnFavoriteStationsLoaded
        }
    }

    fun deleteStationFromFavorite(id:Int){
        viewModelScope.launch {
            localRepository.deleteStationFromFavorite(id)
        }
    }

    sealed class State {
        object OnFavoriteStationsLoaded : State()
    }
}