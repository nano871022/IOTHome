package co.com.japl.iothome.ui.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import co.com.japl.iothome.R
import co.com.japl.iothome.dto.DeviceDTO

class ListDeviceAdapter(private val inflater : LayoutInflater,private val context : FragmentActivity,private val devices : List<DeviceDTO>)
    : ArrayAdapter<DeviceDTO>(context, R.layout.item_device,devices){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.item_device,null,true)

        val name = rowView.findViewById<TextView>(R.id.item_field_device_label) as TextView
        val location = rowView.findViewById<TextView>(R.id.item_field_device_value) as TextView
        val image = rowView.findViewById<TextView>(R.id.item_image_device) as ImageView

        name.text = devices[position].name
        location.text = devices[position].location
        //image.setImageResource(devices[position].image.toString())
        rowView.isVisible = true
        return rowView
    }
}