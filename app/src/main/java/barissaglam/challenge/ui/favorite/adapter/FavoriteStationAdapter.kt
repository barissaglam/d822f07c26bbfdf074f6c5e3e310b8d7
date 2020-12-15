package barissaglam.challenge.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import barissaglam.challenge.R
import barissaglam.challenge.base.adapter.BaseViewHolder
import barissaglam.challenge.data.uimodel.FavoriteStationUIModel
import barissaglam.challenge.databinding.ItemFavoriteBinding

class FavoriteStationAdapter : ListAdapter<FavoriteStationUIModel, BaseViewHolder<*>>(FavoriteItemDiffCallback.diffCallback) {
    var onFavoriteItemClick: ((FavoriteStationUIModel, ItemClickType) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemType.values()[viewType].onCreateViewHolder(parent, layoutInflater,onFavoriteItemClick)
    }

    override fun getItemViewType(position: Int): Int {
        return ItemType.TYPE_FAVORITE.viewType()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        if (position == itemCount) return
        when (holder) {
            is FavoriteStationViewHolder -> holder.bind(getItem(position))
        }
    }

    enum class ItemType {
        TYPE_FAVORITE {
            override fun onCreateViewHolder(parent: ViewGroup, layoutInflater: LayoutInflater,onFavoriteItemClick: ((FavoriteStationUIModel, ItemClickType) -> Unit)?): BaseViewHolder<*> {
                val binding = DataBindingUtil.inflate<ItemFavoriteBinding>(layoutInflater, R.layout.item_favorite, parent, false)
                return FavoriteStationViewHolder(binding,onFavoriteItemClick)
            }
        };

        abstract fun onCreateViewHolder(
            parent: ViewGroup,
            layoutInflater: LayoutInflater,
            onFavoriteItemClick: ((FavoriteStationUIModel, ItemClickType) -> Unit)?
        ): BaseViewHolder<*>

        fun viewType(): Int = ordinal
    }

    enum class ItemClickType {
        FAVORITE_BUTTON
    }
}