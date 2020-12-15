package barissaglam.challenge.data.uimodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableInt
import barissaglam.challenge.BR

data class SpaceVehicleUIModel(
    var id: Int,
    var vehicleName: String,
    var durability: Int,
    var speed: Int,
    var capacity: Int,
    var damageCapacity: Int
) : BaseObservable() {
    private var UGS = capacity * 10000
    private var EUS = (speed * 20)
    private var DS = durability * 10000

    var _UGS: Int
        @Bindable get() = UGS
        set(value) {
            UGS = value
            notifyPropertyChanged(BR._UGS)
        }

    var _EUS: Int
        @Bindable get() = EUS
        set(value) {
            EUS = value
            notifyPropertyChanged(BR._EUS)
        }

    var _DS: Int
        @Bindable get() = DS
        set(value) {
            DS = value
            notifyPropertyChanged(BR._DS)
        }

    var _damageCapacity: Int
        @Bindable get() = damageCapacity
        set(value) {
            damageCapacity = value
            notifyPropertyChanged(BR._damageCapacity)
        }
}