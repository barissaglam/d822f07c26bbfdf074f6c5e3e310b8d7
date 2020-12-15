package barissaglam.challenge.data.remote.model

import com.google.gson.annotations.SerializedName

data class SpaceStation(
    @SerializedName("name") val name:String?,
    @SerializedName("coordinateX") val coordinateX:Double?,
    @SerializedName("coordinateY") val coordinateY:Double?,
    @SerializedName("capacity") val capacity:Int?,
    @SerializedName("stock") val stock:Int?,
    @SerializedName("need") val need:Int?
)
