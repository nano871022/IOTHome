package co.com.japl.iothome.ui.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import co.com.japl.iothome.MainActivity
import co.com.japl.iothome.R
import co.com.japl.iothome.def.IQueryDB
import co.com.japl.iothome.dto.DeviceDTO
import co.com.japl.iothome.ui.custom.ListDeviceAdapter
import co.com.japl.iothome.ui.device.DeviceFragment
import co.com.japl.iothome.ui.device.DeviceFragmentDirections
import co.com.japl.iothome.util.DevicesQueryDB
import kotlin.math.log

class DevicesFragment : Fragment() {

    private lateinit var galleryViewModel: DevicesViewModel
    private lateinit var query : IQueryDB<DeviceDTO>
    private lateinit var root : View
    private lateinit var list : List<DeviceDTO>
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
                ViewModelProviders.of(this).get(DevicesViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_devices, container, false)
        query = DevicesQueryDB(root.context)
        /*
        val textView: TextView = root.findViewById(R.id.text_gallery)
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
         */

        loadList()
        if(list.isNotEmpty()){
            loadDataOnList(inflater,container)
        }else { Toast.makeText(root.context,"Devices wasn\'t found",Toast.LENGTH_LONG).show() }
        return root
    }

    private fun loadList(){
        try {
            list = query.selectAll()
        }catch(e:Exception){
            Toast.makeText(root.context,e.message,Toast.LENGTH_LONG).show()
        }
    }

    private fun loadDataOnList(inflater: LayoutInflater,container: ViewGroup?){
        var listView = root.findViewById<ListView>(R.id.list_devices)
        val activity = this.activity

        if(activity != null  && list.isNotEmpty()) {
            val adapter = ListDeviceAdapter(inflater,activity, list)
            listView.adapter = adapter
            listView.setOnItemClickListener(){adapter,view,position,id->
                val itemPosition = adapter.getItemAtPosition(position)
                val itemId = adapter.getItemIdAtPosition(position)
                //Toast.makeText(root.context,"Click over $itemPosition with id is $itemId",Toast.LENGTH_LONG).show()
                val action = DevicesFragmentDirections.actionNavDevicesToNavDevice(itemId)
                view.findNavController().navigate(action)
            }
        }else{
            Toast.makeText(root.context,"ListView cann\'t show  variables $activity , ${list.size}",Toast.LENGTH_LONG).show()
        }
    }
}