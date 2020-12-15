package barissaglam.challenge.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "space_vehicle")
data class SpaceVehicleEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "vehicle_name") val vehicleName: String,
    @ColumnInfo(name = "durability") val durability: Int,
    @ColumnInfo(name = "speed") val speed: Int,
    @ColumnInfo(name = "capacity") val capacity: Int,
    @ColumnInfo(name = "damageCapacity") val damageCapacity: Int = 100,
)