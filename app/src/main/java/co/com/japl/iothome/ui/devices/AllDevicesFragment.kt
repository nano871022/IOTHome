package co.com.japl.iothome.ui.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import co.com.japl.iothome.R
import co.com.japl.iothome.def.IQueryDB
import co.com.japl.iothome.dto.ConnectionDTO
import co.com.japl.iothome.dto.DeviceDTO
import co.com.japl.iothome.specdb.ConnectionSpecDB
import co.com.japl.iothome.util.ConnectionQueryDB
import co.com.japl.iothome.util.DevicesQueryDB
import co.com.japl.iothome.util.SpreadSheetDownload
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread

class AllDevicesFragment : Fragment() {
    private val numRows = 10
    private val numBeginRow = 0
    private var rowsAll = 0
    private lateinit var root : View
    private val args : AllDevicesFragmentArgs by navArgs()
    private val queryCountRows = "SELECT COUNT(A)"
    private val querySelectLimit = "SELECT * LIMIT $numRows offset "
    private lateinit var connect : ConnectionDTO
    private lateinit var device : DeviceDTO
    private val tableLayout by lazy { TableLayout(this.context) }
    private lateinit var queryConnect : IQueryDB<ConnectionDTO>
    private lateinit var queryDevice : IQueryDB<DeviceDTO>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_all_records, container, false)
        queryConnect = ConnectionQueryDB(root.context)
        queryDevice = DevicesQueryDB(root.context)
        connect = queryConnect.selectAll().first()
        device = queryDevice.select(ConnectionSpecDB.SELECT_NAME,arrayOf(args.nameKey))
        getCount()
        loadRows()
        Thread.sleep(5000)
        return root
    }
    private fun loadRows(){

        Thread(
            Runnable {
                while(rowsAll ==0 ){
                    Thread.sleep(300)

                }
             if(rowsAll != -1){
                    val response = SpreadSheetDownload(
                        connect.token,
                        device.location,
                        querySelectLimit + numBeginRow
                    ).load()
                    response.let {
                        val jsonArray = JSONArray(it)
                        val cantidad = jsonArray.length()
                        var i = 0
                        while(i < cantidad) {
                            val item = jsonArray.getJSONObject(i)
                            val tr = TableRow(this.context)
                            for(key in item.keys()) {
                                val tw = TextView(activity)
                                tw.text = item.getString(key)
                                tr.addView(tw)
                            }
                            activity.let {
                                it?.runOnUiThread(java.lang.Runnable {
                                    //val tableLayout = root.findViewById<TableLayout>(R.id.tabla_layout)
                                    tableLayout.addView(tr)
                                })
                            }
                            i++
                        }
                    }
                }
            }).start()
    }
    private fun getCount(){
        Thread(
            Runnable {
                val response =
                    SpreadSheetDownload(connect.token, device.location, queryCountRows).load()
                response.let {
                    val analize = it?.isNotBlank()
                    if (analize != null && analize) {
                        val json = JSONObject(it)
                        for (key in json.keys()) {
                            rowsAll = json.getInt(key)
                        }
                    } else {
                        Toast.makeText(
                            root.context,
                            "No se encontraron datos con la consulta",
                            Toast.LENGTH_LONG
                        ).show()
                        rowsAll = -1
                    }
                }
            }).start()
    }

    private fun loadTable(){

    }
}