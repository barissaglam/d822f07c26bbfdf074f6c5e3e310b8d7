package barissaglam.challenge.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import barissaglam.challenge.base.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutResourceId: Int
    protected abstract val viewModel: VM

    lateinit var binding: DB
    private var hasRequestSend = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,layoutResourceId)

        intent.extras?.let { viewModel.handleIntent(it) }

        setUpViewModelStateObservers()
        setupClickListeners()
        init()

        if (!hasRequestSend) {
            initStartRequest()
            hasRequestSend = true
        }
    }

    open fun initStartRequest() {}
    open fun init() {}
    open fun setUpViewModelStateObservers() {}
    open fun setupClickListeners() {}


}