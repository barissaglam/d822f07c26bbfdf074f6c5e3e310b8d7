package barissaglam.challenge.ui.define

import android.util.Log
import androidx.databinding.ObservableInt
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import barissaglam.challenge.base.viewmodel.BaseViewModel
import barissaglam.challenge.data.local.LocalRepository
import barissaglam.challenge.data.local.model.SpaceVehicleEntity
import com.google.gson.Gson
import kotlinx.coroutines.launch

class DefineSpaceVehicleViewModel @ViewModelInject constructor(private val localRepository: LocalRepository) : BaseViewModel() {
    var durabilityPoint = ObservableInt(MIN_SEEK_BAR_VALUE)
    var speedPoint = ObservableInt(MIN_SEEK_BAR_VALUE)
    var capacityPoint = ObservableInt(MIN_SEEK_BAR_VALUE)

    fun defineSpaceVehicle(vehicleName:String){
        val spaceVehicleEntity = SpaceVehicleEntity(
            vehicleName = vehicleName,
            durability = durabilityPoint.get(),
            speed = speedPoint.get(),
            capacity = capacityPoint.get()
        )
        viewModelScope.launch {
            localRepository.defineSpaceVehicle(spaceVehicleEntity)
        }
    }

    companion object {
        const val MIN_SEEK_BAR_VALUE = 1
        const val MAX_TOTAL_SEEK_BAR_VALUE = 15
    }
}