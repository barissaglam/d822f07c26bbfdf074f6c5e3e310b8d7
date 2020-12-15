package barissaglam.challenge.data.uimodel

import androidx.databinding.*
import barissaglam.challenge.BR
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.sqrt

data class SpaceStationUIModel(
    val name: ObservableField<String>,
    val coordinateX: Double,
    val coordinateY: Double,
    val capacity: ObservableInt,
    val stock: ObservableInt,
    val need: ObservableInt
) : BaseObservable() {

    val isVisited:ObservableBoolean = ObservableBoolean(false)

    val isFavorite:ObservableBoolean = ObservableBoolean(false)

    private var _distanceToWorld: Double =
        DecimalFormat("#.##").format(sqrt((coordinateX - 0.0).pow(2.0) + (coordinateY - 0.0).pow(2.0))).toDouble()

    var distanceToWorld: Double
        @Bindable get() = _distanceToWorld
        set(value) {
            _distanceToWorld = value
            notifyPropertyChanged(BR.distanceToWorld)
        }
}