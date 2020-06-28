package co.com.japl.iothome.ui.device

import android.net.UrlQuerySanitizer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import co.com.japl.iothome.R
import co.com.japl.iothome.def.IQueryDB
import co.com.japl.iothome.dto.ConnectionDTO
import co.com.japl.iothome.dto.DeviceDTO
import co.com.japl.iothome.specdb.DevicesSpecDB
import co.com.japl.iothome.ui.custom.ListItemFieldDeviceAdapter
import co.com.japl.iothome.util.ConnectionQueryDB
import co.com.japl.iothome.util.DevicesQueryDB
import co.com.japl.iothome.util.SpreadSheetDownload
import co.com.japl.iothome.util.ValuePair
import org.json.JSONObject

class DeviceFragment : Fragment() {

    private lateinit var slideshowViewModel: DeviceViewModel
    private val args : DeviceFragmentArgs by navArgs()
    private lateinit var queryDevice : IQueryDB<DeviceDTO>
    private lateinit var queryConnection : IQueryDB<ConnectionDTO>
    private lateinit var dto : DeviceDTO
    private lateinit var connect : ConnectionDTO
    private val listFields = arrayListOf<ValuePair>()
    private val query = "SELECT * ORDER BY A DESC LIMIT 1"
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        
        slideshowViewModel =
                ViewModelProviders.of(this).get(DeviceViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_device, container, false)
        queryDevice = DevicesQueryDB(root.context)
        queryConnection = ConnectionQueryDB(root.context)
        dto = queryDevice.selectById(args.argDeviceId)
        connect = queryConnection.selectAll().first()
        loadSpreadSheet()
        listFields()
        val edit = root.findViewById<Button>(R.id.button_edit_device)
        edit.setOnClickListener{
            var navigate = DeviceFragmentDirections.actionNavDeviceToNavAddDevice(args.argDeviceId)
            it.findNavController().navigate(navigate)
            //Navigation.findNavController(it).navigate(R.id.nav_add_device)
        }

        loadFields(inflater,root)
        return root
    }

    private fun loadSpreadSheet(){
        Thread(
            Runnable {
                val response = SpreadSheetDownload(connect.token,dto.location,query).load()
                var list = arrayListOf<ValuePair>()
                response?.let{
                    var json = JSONObject(it)
                    for( key in json.keys()){
                        var vp = ValuePair()
                        vp.key = key
                        vp.value = json.getString(vp.key)
                        list.add(vp)
                    }
                }
                listFields.addAll(list)
            }).start()
    }

    private fun loadFields(inflater: LayoutInflater,root:View){
        var listFields = root.findViewById<ListView>(R.id.list_fields)
        val activity = this.activity
        if(activity != null) {
            Thread.sleep(2000)
            var adapter = ListItemFieldDeviceAdapter(inflater, activity, this.listFields)
            listFields.adapter = adapter
        }
    }

    private fun listFields(){
        var list = arrayListOf<ValuePair>()
        dto.name.let {
            val valuePair = ValuePair()
            valuePair.key = "Name"
            valuePair.value = it
            list.add(valuePair)
        }
        dto.location.let {
            var valuePair = ValuePair()
            valuePair.key = "Location"
            valuePair.value = it
            list.add(valuePair)
        }
        listFields.addAll(0,list)
    }


}