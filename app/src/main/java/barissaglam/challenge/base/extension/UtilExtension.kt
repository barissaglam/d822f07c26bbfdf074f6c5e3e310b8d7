package barissaglam.challenge.base.extension

import android.widget.SeekBar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

inline fun <T> LiveData<T>.observeNonNull(
    owner: LifecycleOwner,
    crossinline observer: (t: T) -> Unit
) {
    this.observe(owner, androidx.lifecycle.Observer {
        it?.let(observer)
    })
}


fun SeekBar.updateListener(updateBlock: (seekBar: SeekBar?, progress: Int, fromUser: Boolean) -> Unit) {
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            updateBlock(seekBar, progress, fromUser)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    })
}


fun Double?.orZero():Double = this ?: 0.0
fun Int?.orZero():Int = this ?: 0
fun Long?.orZero():Long = this ?: 0L

fun Int?.notNegative() = if (this?:0 < 0) 0 else this