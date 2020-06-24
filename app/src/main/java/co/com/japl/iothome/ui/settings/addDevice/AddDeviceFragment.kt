package co.com.japl.iothome.ui.settings.addDevice

import android.bluetooth.BluetoothClass
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import co.com.japl.iothome.R
import co.com.japl.iothome.def.IQueryDB
import co.com.japl.iothome.dto.ConnectionDTO
import co.com.japl.iothome.dto.DeviceDTO
import co.com.japl.iothome.util.DevicesQueryDB
import com.google.android.material.textfield.TextInputEditText

class AddDeviceFragment : Fragment() {

    private lateinit var addDeviceViewModel: AddDeviceViewModel
    private lateinit var db : IQueryDB<DeviceDTO>
    private lateinit var dto : DeviceDTO
    private lateinit var root : View
    private val args : AddDeviceFragmentArgs by navArgs()
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        addDeviceViewModel =
                ViewModelProviders.of(this).get(AddDeviceViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_add_device, container, false)
        dto = DeviceDTO()
        db = DevicesQueryDB(root.context)
        root.findViewById<Button>(R.id.button_save).setOnClickListener{ save() }
        /*val textView: TextView = root.findViewById(R.id.text_gallery)
        addDeviceViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        load(db.selectById(args.idDevice))
        return root
    }

    fun load(dto:DeviceDTO){
        this.dto = dto
        root.findViewById<TextInputEditText>(R.id.field_name_device).setText(dto.name)
        root.findViewById<TextInputEditText>(R.id.field_location_device).setText(dto.location)
        
    }

    private fun load(){
        dto.name = root.findViewById<TextInputEditText>(R.id.field_name_device).text.toString()
        dto.location = root.findViewById<TextInputEditText>(R.id.field_location_device).text.toString()

    }

    private fun insert(){
        if(db.insertRow(dto)) {
            Toast.makeText(root.context, "Device was added", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(root.context, "Device wasn\'t added", Toast.LENGTH_LONG).show()
        }
    }

    private fun update(){
        if(db.updateRow(dto)){
            Toast.makeText(root.context,"Device was updated",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(root.context,"Device wasn\'t updated",Toast.LENGTH_LONG).show()
        }
    }

    fun save(){
        load()
        if(dto._id > 0){
            update()
        }else{
            insert()
        }
    }


}