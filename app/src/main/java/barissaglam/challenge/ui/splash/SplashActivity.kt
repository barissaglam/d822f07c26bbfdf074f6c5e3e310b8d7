package barissaglam.challenge.ui.splash

import android.animation.ObjectAnimator
import android.content.res.Resources
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import barissaglam.challenge.MainActivity
import barissaglam.challenge.R
import barissaglam.challenge.base.extension.observeNonNull
import barissaglam.challenge.base.view.BaseActivity
import barissaglam.challenge.databinding.ActivitySplashBinding
import barissaglam.challenge.ui.define.DefineSpaceVehicleActivity
import barissaglam.challenge.ui.favorite.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    override val layoutResourceId: Int = R.layout.activity_splash
    override val viewModel: SplashViewModel by viewModels()

    override fun init() {
        setupAnimation()
    }

    override fun setUpViewModelStateObservers() {
        viewModel.stateLiveData.observeNonNull(this, {
            when (it) {
                is SplashViewModel.State.OnSpaceVehiclesLoaded -> onSpaceVehiclesLoaded(it.vehicleCount)
            }
        })
    }

    private fun onSpaceVehiclesLoaded(vehicleCount: Int) {
        if (vehicleCount > 0) MainActivity.start(this)
        else DefineSpaceVehicleActivity.start(this)

        supportFinishAfterTransition()
    }

    private fun setupAnimation() {
        binding.lottieAnimationViewLogo.post {
            val targetY = binding.lottieAnimationViewLogo.y

            ObjectAnimator.ofFloat(binding.lottieAnimationViewLogo, "y", Resources.getSystem().displayMetrics.heightPixels.toFloat(), targetY).apply {
                duration = 1500
                interpolator = DecelerateInterpolator()
                start()
            }

            ObjectAnimator.ofFloat(binding.textViewWelcome,"alpha",0f,0.5f,1.0f).apply {
                duration = 2000
                startDelay = 1500
                start()
                doOnEnd { viewModel.getSpaceVehicles() }
            }
        }

    }
}