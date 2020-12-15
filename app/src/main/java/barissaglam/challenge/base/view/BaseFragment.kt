package barissaglam.challenge.base.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import barissaglam.challenge.R
import barissaglam.challenge.base.extension.observeNonNull
import barissaglam.challenge.base.viewmodel.BaseViewModel
import barissaglam.challenge.ui.station.StationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    @get:LayoutRes
    protected abstract val layoutResourceId: Int
    protected abstract val viewModel: VM

    lateinit var binding: DB

    // lateinit var viewModel: VM
    private var hasRequestSend = false

    private var progressDialog: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let { viewModel.handleIntent(it) }

        setUpViewModelStateObservers()
        setupClickListeners()
        init()

        viewModel.baseLiveData.observeNonNull(viewLifecycleOwner, {
            when (it) {
                is BaseViewModel.State.ShowLoading -> {
                    showProgress()
                }
                is BaseViewModel.State.ShowContent -> {
                    dismissProgress()
                }
                is BaseViewModel.State.OnError -> {
                    dismissProgress()
                    showDialog(message = "Bilinmeyen bir hata oluştu"){}
                }
            }
        })

        if (!hasRequestSend) {
            initStartRequest()
            hasRequestSend = true
        }
    }


    fun showDialog(title: String? = "Uyarı", message: String, positiveBlock: () -> Unit) {
        if (activity != null) {
            AlertDialog.Builder(requireActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Anladım") { _, _ ->
                    positiveBlock()
                }.setCancelable(false).show()
        }
    }

    fun showProgress() {
        if (progressDialog == null) {
            progressDialog = Dialog(requireContext()).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(false)
                setContentView(R.layout.dialog_progress)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
        if (progressDialog?.isShowing == false)
            progressDialog!!.show()
    }

    fun dismissProgress() {
        progressDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    open fun initStartRequest() {}
    open fun init() {}
    open fun setUpViewModelStateObservers() {}
    open fun setupClickListeners() {}


}