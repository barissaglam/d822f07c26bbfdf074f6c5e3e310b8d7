package barissaglam.challenge.data.uimodel

import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.sqrt

data class FavoriteStationUIModel(
    val id : Int,
    val name:String,
    val coordinateX:Double,
    val coordinateY:Double,
) {
    val distanceToWorld: Double = DecimalFormat("#.##").format(sqrt((coordinateX - 0.0).pow(2.0) + (coordinateY - 0.0).pow(2.0))).toDouble()
}