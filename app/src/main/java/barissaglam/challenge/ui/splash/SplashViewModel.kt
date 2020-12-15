package barissaglam.challenge.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import barissaglam.challenge.base.viewmodel.BaseViewModel
import barissaglam.challenge.data.local.LocalRepository
import barissaglam.challenge.data.uimodel.SpaceVehicleUIModel
import kotlinx.coroutines.launch

class SplashViewModel @ViewModelInject constructor(private val localRepository: LocalRepository) : BaseViewModel() {
    val stateLiveData = MutableLiveData<State>()

    fun getSpaceVehicles() {
        viewModelScope.launch {
            localRepository.getSpaceVehicles().sendLocalRequest {
                stateLiveData.value = State.OnSpaceVehiclesLoaded(it.size)
            }
        }
    }

    sealed class State {
        data class OnSpaceVehiclesLoaded(val vehicleCount : Int) : State()
    }
}