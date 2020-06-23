package co.com.japl.iothome.ui.alert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.com.japl.iothome.R

class AlertFragment : Fragment() {

    private lateinit var alertViewModel: AlertViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        alertViewModel =
                ViewModelProviders.of(this).get(AlertViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_alert, container, false)
        /*val textView: TextView = root.findViewById(R.id.tittle_devices)
        alertViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }
}