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
import co.com.japl.iothome.util.ValuePair

class ListItemFieldDeviceAdapter(private val inflater : LayoutInflater, private val context : FragmentActivity, private val values : List<ValuePair>)
    : ArrayAdapter<ValuePair>(context, R.layout.item_field_device,values){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.item_field_device,null,true)

        val label = rowView.findViewById<TextView>(R.id.item_field_device_label) as TextView
        val value = rowView.findViewById<TextView>(R.id.item_field_device_value) as TextView

        label.text = values[position].key
        value.text = values[position].value

        rowView.isVisible = true
        return rowView
    }
}