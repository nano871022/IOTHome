package co.com.japl.iothome.ui.settings.addDevice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddDeviceViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is add device Fragment"
    }
    val text: LiveData<String> = _text
}