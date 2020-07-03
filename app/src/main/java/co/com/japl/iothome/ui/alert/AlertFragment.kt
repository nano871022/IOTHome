package co.com.japl.iothome.ui.alert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import co.com.japl.iothome.R
import co.com.japl.iothome.def.IQueryDB
import co.com.japl.iothome.dto.ConnectionDTO
import co.com.japl.iothome.dto.DeviceDTO
import co.com.japl.iothome.ui.custom.ListItemFieldDeviceAdapter
import co.com.japl.iothome.ui.device.DeviceFragmentArgs
import co.com.japl.iothome.ui.devices.AllDevicesFragmentDirections
import co.com.japl.iothome.ui.devices.DevicesFragmentDirections
import co.com.japl.iothome.util.ConnectionQueryDB
import co.com.japl.iothome.util.DevicesQueryDB
import co.com.japl.iothome.util.SpreadSheetDownload
import co.com.japl.iothome.util.ValuePair
import org.json.JSONObject

class AlertFragment : Fragment() {

    private lateinit var alertViewModel: AlertViewModel
    private val args : DeviceFragmentArgs by navArgs()
    private lateinit var queryConnect : IQueryDB<ConnectionDTO>
    private lateinit var queryDevice : IQueryDB<DeviceDTO>
    private var listCount = arrayListOf<ValuePair>()
    private val query = "SELECT COUNT(A)"
    private lateinit var connect : ConnectionDTO
    private lateinit var devices : List<DeviceDTO>
    private lateinit var root : View
    private lateinit var adapter: ListItemFieldDeviceAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        alertViewModel =
                ViewModelProviders.of(this).get(AlertViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_alert, container, false)
        queryDevice = DevicesQueryDB(root.context)
        queryConnect = ConnectionQueryDB(root.context)
        connect = queryConnect.selectAll().first()
        devices = queryDevice.selectAll()
        loadSpreadSheet()
        loadList()
        return root
    }

    private fun loadList(){
        val inflater = this.layoutInflater
        val activity = this.activity
        val listAlerts = root.findViewById<ListView>(R.id.list_alerts)
        Thread.sleep(3000)
        if(activity != null && inflater != null) {
            adapter = ListItemFieldDeviceAdapter(inflater, activity, this.listCount)
            listAlerts.adapter = adapter
            listAlerts.setOnItemClickListener() { adapter, view, position, id ->
                val itemPosition = adapter.getItemAtPosition(position) as ValuePair
                val action = AlertFragmentDirections.actionNavAlertToNavRecords(itemPosition.key)
                view.findNavController().navigate(action)
            }
        }
    }

    private fun loadSpreadSheet(){
        Thread(
            Runnable {
                    for(dto in devices){
                        val response = SpreadSheetDownload(connect.token, dto.location, query).load()
                        val list = arrayListOf<ValuePair>()
                        response?.let {
                            var json = JSONObject(it)
                            for (key in json.keys()) {
                                var vp = ValuePair()
                                vp.key = dto.name
                                vp.value = json.getString(key)
                                list.add(vp)
                            }
                            listCount = list
                            notifyLoadData()
                        }
                    }
            }).start()
    }

    private fun notifyLoadData(){
        activity.let{it?.runOnUiThread(java.lang.Runnable {
            adapter.let { it.notifyDataSetChanged() }
        })}
    }

}