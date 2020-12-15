package barissaglam.challenge.base.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import barissaglam.challenge.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseViewModel : ViewModel() {
    open fun handleIntent(extras: Bundle) {}

    val baseLiveData: MutableLiveData<State> = MutableLiveData()

    inline fun <T> Flow<Resource<T>>.sendRequest(crossinline onComplete: (T) -> Unit) {
        onEach { response ->
            when (response) {
                is Resource.Loading -> {
                    baseLiveData.value = State.ShowLoading
                }
                is Resource.Success -> {
                    onComplete(response.data)
                    baseLiveData.value = State.ShowContent
                }
                is Resource.Error -> {
                    baseLiveData.value = State.OnError(response.exception)
                }
            }
        }.launchIn(viewModelScope)
    }

    inline fun <T> Flow<T>.sendLocalRequest(crossinline onComplete: (T) -> Unit) {
        onEach { onComplete(it) }.launchIn(viewModelScope)
    }

    sealed class State {
        data class OnError(val throwable: Throwable) : State()
        object ShowLoading:State()
        object ShowContent : State()
    }

}