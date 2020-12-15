package barissaglam.challenge.ui.favorite

import androidx.fragment.app.viewModels
import barissaglam.challenge.R
import barissaglam.challenge.base.extension.observeNonNull
import barissaglam.challenge.base.view.BaseFragment
import barissaglam.challenge.data.uimodel.FavoriteStationUIModel
import barissaglam.challenge.databinding.FragmentFavoriteBinding
import barissaglam.challenge.ui.favorite.adapter.FavoriteStationAdapter
import barissaglam.challenge.ui.station.StationViewModel
import barissaglam.challenge.ui.station.adapter.StationPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment: BaseFragment<FragmentFavoriteBinding,FavoriteViewModel>() {
    override val layoutResourceId: Int = R.layout.fragment_favorite
    override val viewModel: FavoriteViewModel by viewModels()

    private val favoriteStationAdapter by lazy { FavoriteStationAdapter() }

    override fun init() {
        binding.vm = viewModel
    }

    override fun setUpViewModelStateObservers() {
        viewModel.stateLiveData.observeNonNull(viewLifecycleOwner, {
            when (it) {
                is FavoriteViewModel.State.OnFavoriteStationsLoaded -> setupFavoriteRecyclerView()
            }
        })
    }

    private fun setupFavoriteRecyclerView() {
        favoriteStationAdapter.submitList(viewModel.favoriteList)
        favoriteStationAdapter.onFavoriteItemClick = ::onFavoriteAdapterItemClick
        binding.recyclerViewFavorite.adapter = favoriteStationAdapter
    }

    private fun onFavoriteAdapterItemClick(favoriteStationUIModel: FavoriteStationUIModel, itemClickType: FavoriteStationAdapter.ItemClickType) {
        when (itemClickType) {
            FavoriteStationAdapter.ItemClickType.FAVORITE_BUTTON -> onFavoriteButtonClicked(favoriteStationUIModel)
        }
    }

    private fun onFavoriteButtonClicked(favoriteStationUIModel: FavoriteStationUIModel) {
        viewModel.deleteStationFromFavorite(favoriteStationUIModel.id)
    }

}