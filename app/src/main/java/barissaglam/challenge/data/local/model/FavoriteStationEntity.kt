package barissaglam.challenge.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_station")
data class FavoriteStationEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "station_name") val stationName: String,
    @ColumnInfo(name = "coordinateX") val coordinateX: Double,
    @ColumnInfo(name = "coordinateY") val coordinateY: Double
)