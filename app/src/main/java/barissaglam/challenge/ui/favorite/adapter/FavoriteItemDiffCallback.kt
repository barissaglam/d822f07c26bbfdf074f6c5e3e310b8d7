package barissaglam.challenge.ui.favorite.adapter

import androidx.recyclerview.widget.DiffUtil
import barissaglam.challenge.data.uimodel.FavoriteStationUIModel

object FavoriteItemDiffCallback {
    val diffCallback = object : DiffUtil.ItemCallback<FavoriteStationUIModel>() {
        override fun areItemsTheSame(oldItem: FavoriteStationUIModel, newItem: FavoriteStationUIModel): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: FavoriteStationUIModel, newItem: FavoriteStationUIModel): Boolean = oldItem.id == newItem.id
    }
}