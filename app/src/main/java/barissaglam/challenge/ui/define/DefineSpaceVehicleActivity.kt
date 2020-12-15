package barissaglam.challenge.ui.define

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import barissaglam.challenge.MainActivity
import barissaglam.challenge.R
import barissaglam.challenge.base.extension.updateListener
import barissaglam.challenge.base.view.BaseActivity
import barissaglam.challenge.databinding.ActivityDefineSpaceVehicleBinding
import barissaglam.challenge.ui.define.DefineSpaceVehicleViewModel.Companion.MAX_TOTAL_SEEK_BAR_VALUE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DefineSpaceVehicleActivity : BaseActivity<ActivityDefineSpaceVehicleBinding, DefineSpaceVehicleViewModel>() {
    override val layoutResourceId: Int = R.layout.activity_define_space_vehicle
    override val viewModel: DefineSpaceVehicleViewModel by viewModels()

    override fun init() {
        binding.vm = viewModel
        setupSeekBars()

        Log.i("testtest","init")
    }

    override fun setupClickListeners() {
        binding.buttonSave.setOnClickListener {
            if (isValid()){
                viewModel.defineSpaceVehicle(binding.textInputEditTextName.text.toString())
                MainActivity.start(this)
                supportFinishAfterTransition()
            }
        }
    }

    private fun setupSeekBars() {
        binding.seekBarDurability.updateListener { seekBar, progress: Int, _ ->
            if (progress + viewModel.capacityPoint.get() + viewModel.speedPoint.get() > MAX_TOTAL_SEEK_BAR_VALUE) {
                seekBar?.progress = viewModel.durabilityPoint.get()
            } else viewModel.durabilityPoint.set(progress)
        }

        binding.seekBarCapacity.updateListener { seekBar, progress, _ ->
            if (progress + viewModel.durabilityPoint.get() + viewModel.speedPoint.get() > MAX_TOTAL_SEEK_BAR_VALUE) {
                seekBar?.progress = viewModel.capacityPoint.get()
            } else viewModel.capacityPoint.set(progress)
        }

        binding.seekBarSpeed.updateListener { seekBar, progress, _ ->
            if (progress + viewModel.capacityPoint.get() + viewModel.durabilityPoint.get() > MAX_TOTAL_SEEK_BAR_VALUE) {
                seekBar?.progress = viewModel.speedPoint.get()
            } else
                viewModel.speedPoint.set(progress)
        }
    }

    private fun isValid(): Boolean {
        if (binding.textInputEditTextName.text.toString().isEmpty()) {
            binding.textInputLayoutName.error = "Bu alan boş bırakılamaz."
            return false
        } else if (viewModel.capacityPoint.get() + viewModel.speedPoint.get() + viewModel.durabilityPoint.get() < 15) {
            AlertDialog.Builder(this)
                .setTitle("Uyarı")
                .setMessage("Dayanıklılık, Kapasite ve Hız alanlarının toplam puanı 15 olacak şekilde dağıtmalısınız.")
                .setPositiveButton("Anladım") { _, _ ->

                }.setCancelable(false).show()
            return false
        }

        return true
    }

    override fun onStart() {
        super.onStart()
        Log.i("testtest","onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.i("testtest","onStart")
    }

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, DefineSpaceVehicleActivity::class.java))
        }
    }
}
