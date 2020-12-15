package barissaglam.challenge.ui.favorite.adapter

import barissaglam.challenge.base.adapter.BaseViewHolder
import barissaglam.challenge.data.uimodel.FavoriteStationUIModel
import barissaglam.challenge.databinding.ItemFavoriteBinding

class FavoriteStationViewHolder(private val binding:ItemFavoriteBinding,private val onFavoriteItemClick: ((FavoriteStationUIModel, FavoriteStationAdapter.ItemClickType) -> Unit)?):BaseViewHolder<FavoriteStationUIModel>(binding.root) {
    override fun bind(data: FavoriteStationUIModel) {
        binding.data = data

        binding.imageViewFavorite.setOnClickListener {
            onFavoriteItemClick?.invoke(data,FavoriteStationAdapter.ItemClickType.FAVORITE_BUTTON)
        }
    }
}