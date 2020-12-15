package barissaglam.challenge.ui.station

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import barissaglam.challenge.R
import barissaglam.challenge.base.extension.observeNonNull
import barissaglam.challenge.base.extension.orZero
import barissaglam.challenge.base.view.BaseFragment
import barissaglam.challenge.data.uimodel.SpaceStationUIModel
import barissaglam.challenge.databinding.FragmentStationBinding
import barissaglam.challenge.ui.station.adapter.StationPagerAdapter
import barissaglam.challenge.util.ThemeUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt


@AndroidEntryPoint
class StationFragment : BaseFragment<FragmentStationBinding, StationViewModel>() {
    override val layoutResourceId: Int = R.layout.fragment_station
    override val viewModel: StationViewModel by viewModels()

    private val stationPagerAdapter by lazy { StationPagerAdapter() }
    private var countDownTimer: CountDownTimer? = null

    override fun init() {
        binding.vm = viewModel
    }

    override fun initStartRequest() {
        viewModel.getSpaceStations()
    }

    override fun setUpViewModelStateObservers() {
        viewModel.stateLiveData.observeNonNull(viewLifecycleOwner, {
            when (it) {
                is StationViewModel.State.OnSpaceStationsLoaded -> setupSpaceStationViewPager()
            }
        })
    }

    override fun setupClickListeners() {
        binding.imageViewClear.setOnClickListener {
            binding.searchView.setText("")
            binding.searchView.clearFocus()
        }
    }

    private fun setupSpaceStationViewPager() {
        stationPagerAdapter.setItemList(viewModel.spaceStationList)
        stationPagerAdapter.onTravelItemClick = ::onStationPagerAdapterItemClick
        binding.viewPagerStations.pageMargin = ThemeUtil.dpToPx(context, 8)
        binding.viewPagerStations.adapter = stationPagerAdapter

        setupSearchArea()
        startTimer(viewModel.spaceVehicle.get()?._DS?.toLong().orZero())

    }

    private fun onStationPagerAdapterItemClick(position: Int, item: SpaceStationUIModel, itemClickType: StationPagerAdapter.ItemClickType) {
        when (itemClickType) {
            StationPagerAdapter.ItemClickType.TRAVEL_BUTTON -> onTravelButtonClicked(position, item)
            StationPagerAdapter.ItemClickType.FAVORITE_BUTTON -> onFavoriteButtonClicked(position, item)
        }
    }

    private fun onTravelButtonClicked(position: Int, item: SpaceStationUIModel) {
        if (viewModel.isGameFinished) return

        if (item == viewModel.currentSpaceStation.get()) {
            Toast.makeText(this.requireContext(), "Zaten ${item.name.get()} istasyonundasın.", Toast.LENGTH_LONG).show()
            return
        }

        val UGS = (viewModel.spaceVehicle.get()?._UGS.orZero()) - (item.need.get())
        val EUS = (viewModel.spaceVehicle.get()?._EUS.orZero()) - (item.distanceToWorld)

        if (isFinishGame({ UGS <= 0 }, R.string.message_ugs) || isFinishGame({ EUS <= 0 }, R.string.message_eus)) return

        viewModel.spaceVehicle.get()?._UGS = (viewModel.spaceVehicle.get()?._UGS.orZero()) - (item.need.get())
        viewModel.spaceVehicle.get()?._EUS = (viewModel.spaceVehicle.get()?._EUS.orZero()) - (item.distanceToWorld).roundToInt()

        item.need.set(0)
        item.stock.set(item.capacity.get())
        item.isVisited.set(true)

        viewModel.currentSpaceStation.set(item)
        viewModel.updateStationsDistance()

        isFinishGame({ viewModel.getTotalRemainingEUS() > EUS }, R.string.message_ugs)
    }

    private fun onFavoriteButtonClicked(position: Int, item: SpaceStationUIModel) {
        viewModel.addFavoriteStation(item)
    }

    private fun setupSearchArea() {
        val stationNames = viewModel.spaceStationList.map { it.name.get() }.toTypedArray()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, stationNames)

        binding.searchView.setOnItemClickListener { _, _, position, _ ->
            val searchedItem = adapter.getItem(position)
            val searchedItemPosition = viewModel.getSearchedItemPosition(searchedItem.orEmpty())
            binding.viewPagerStations.currentItem = searchedItemPosition
        }

        binding.searchView.setAdapter(adapter)
    }

    private fun startTimer(mls: Long) {
        countDownTimer = object : CountDownTimer(mls, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                val progress: Int = (millisUntilFinished / 1000).toInt()
                binding.textViewTime.text = "$progress s"
            }

            override fun onFinish() {
                val damageCapacity = (viewModel.spaceVehicle.get()?._damageCapacity.orZero() - 10)
                viewModel.spaceVehicle.get()?._damageCapacity = damageCapacity

                if (isFinishGame({ damageCapacity <= 0 }, R.string.message_damage)) return
                startTimer(viewModel.spaceVehicle.get()?._DS?.toLong().orZero())
            }
        }.start()
    }


    private fun isFinishGame(block: () -> Boolean, messageRes: Int): Boolean {
        if (block()) {
            val message = getString(messageRes)
            showDialog(getString(R.string.title_finish_game), message) {
                binding.viewPagerStations.currentItem = 0
                viewModel.currentSpaceStation.set(viewModel.spaceStationList[0])
                viewModel.updateStationsDistance()
                countDownTimer?.cancel()
            }
            viewModel.isGameFinished = true
        }
        return block()
    }
}