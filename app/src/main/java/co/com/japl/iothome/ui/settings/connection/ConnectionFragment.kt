package co.com.japl.iothome.ui.settings.connection

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import co.com.japl.iothome.R
import co.com.japl.iothome.def.IQueryDB
import co.com.japl.iothome.dto.ConnectionDTO
import co.com.japl.iothome.util.ConnectionQueryDB
import com.google.android.material.textfield.TextInputEditText

class ConnectionFragment : Fragment() {

    private lateinit var connectionViewModel: ConnectionViewModel
    private lateinit var db : IQueryDB<ConnectionDTO>
    private lateinit var dto : ConnectionDTO
    private lateinit var root : View
    private var registry = ConnectionDTO()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        connectionViewModel =
                ViewModelProviders.of(this).get(ConnectionViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_connection, container, false)
        db = ConnectionQueryDB(root.context)
        root.findViewById<Button>(R.id.btn_save).setOnClickListener{
            save()
        }
        findInformation()
        return root
    }

    private fun findInformation(){
        try {
            val list = db.selectAll()
            if(!list.isEmpty()) {
                registry = list.first()
                root.findViewById<TextView>(R.id.field_name).text = registry.name
                root.findViewById<TextView>(R.id.field_email).text = registry.email
                root.findViewById<TextView>(R.id.field_token).text = registry.token
                //root.findViewById<ImageView>(R.id.image). = registry.image
            }
        }catch(e:Exception){
            e.printStackTrace()
        }
    }

    private fun loadData(){
        registry.name = root?.findViewById<TextInputEditText>(R.id.field_name)?.text.toString()
        registry.email = root?.findViewById<TextInputEditText>(R.id.field_email)?.text.toString()
        registry.token = root?.findViewById<TextInputEditText>(R.id.field_token)?.text.toString()
        registry.image = ""
    }

    private fun insert(){
        if(db.insertRow(registry)){
            Toast.makeText(root.context,R.string.msg_info_connection_was_added_successfull,Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(root.context,"Information wasn\'t added",Toast.LENGTH_LONG).show()
        }

    }

    private fun update(){
        if(db.updateRow(registry)) {
            Toast.makeText(
                root.context,
                R.string.msn_info_connection_was_updated_successfull,
                Toast.LENGTH_LONG
            ).show()
        }else{
            Toast.makeText(
                root.context,
                R.string.msn_info_connection_wasnt_updated_successfull,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun save(){
        try {
            loadData()
            if(registry._id > -1){
                update()
            }else{
                insert()
            }
        }catch(e:Exception){
            e.printStackTrace()
            Toast.makeText(root.context,R.string.msn_err_connection_was_failed,Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }
}