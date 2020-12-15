package barissaglam.challenge.ui.station.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import barissaglam.challenge.R
import barissaglam.challenge.data.remote.model.SpaceStation
import barissaglam.challenge.data.uimodel.SpaceStationUIModel
import barissaglam.challenge.databinding.ItemSpaceStationBinding

class StationPagerAdapter() : PagerAdapter() {
    private val itemList = ArrayList<SpaceStationUIModel>()
    var onTravelItemClick: ((Int, SpaceStationUIModel, ItemClickType) -> Unit)? = null

    fun setItemList(items: List<SpaceStationUIModel>) {
        itemList.addAll(items)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = DataBindingUtil.inflate<ItemSpaceStationBinding>(
            LayoutInflater.from(container.context),
            R.layout.item_space_station,
            container,
            false
        )
        binding.data = itemList[position]
        container.addView(binding.root)

        binding.buttonTravel.setOnClickListener {
            onTravelItemClick?.invoke(position, itemList[position], ItemClickType.TRAVEL_BUTTON)
        }

        binding.imageViewFavorite.setOnClickListener {
            onTravelItemClick?.invoke(position, itemList[position], ItemClickType.FAVORITE_BUTTON)
        }

        return binding.root
    }

    override fun getCount(): Int = itemList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    enum class ItemClickType {
        TRAVEL_BUTTON,
        FAVORITE_BUTTON
    }
}