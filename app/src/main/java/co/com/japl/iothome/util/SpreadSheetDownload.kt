package co.com.japl.iothome.util

import android.content.Intent
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class SpreadSheetDownload (private val key : String,private val sheetName : String){
    private val query = "SELECT a,b,c WHERE c < 200 AND x = 'yes'"
    private val url = "https://docs.google.com/spreadsheets/d/$key/gviz/tq"
    private val params = "tqx=out:csv&sheet=$sheetName&tq=$query"

    fun load():String?{
        var obj = URL(url)
        with(obj.openConnection() as HttpURLConnection) {
            if (outputStream != null) {
                println("Sending 'GET' request to $url")
                val osw = OutputStreamWriter(outputStream)
                osw.write(params)
                osw.flush()

                println("Response code $responseCode")
                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var line = it.readLine()
                    while (line != null) {
                        response.append(line)
                        line = it.readLine()
                    }
                    it.close()
                    println("POST Response $response")
                    return response.toString()
                }
            }
        }
        return null
    }

}