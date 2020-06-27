package co.com.japl.iothome.util

import android.content.Intent
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class SpreadSheetDownload (private val key : String,private val sheetName : String){
    private val query = "SELECT * ORDER BY A DESC LIMIT 1"
    private val url = "https://docs.google.com/spreadsheets/d/$key/gviz/tq?tqx=out:csv&sheet=$sheetName&tq=$query"

    fun load():String?{
        var response = ""
        try {
            var obj = URL(url)
            with(obj.openConnection() as HttpURLConnection) {
                println("Sending 'GET' request to $url")
                println("Response code $responseCode")
                BufferedReader(InputStreamReader(inputStream)).use {
                    var line = it.readLine()
                    var heads = line.split(",")
                    while (line != null) {
                        line = it.readLine()
                        var body = line.split(",")
                        response += (build(heads,body))
                    }
                    it.close()
                    println("POST Response $response")
                }
            }
        }catch(e:Exception){
            println(e)
        }
        return response
    }

    private fun build(head:List<String>,body:List<String>):String{
        var i = 0
        var line = ""
        while(i < head.size){
            if(i > 0) line += ","
            line += head[i] + " : "+body[i]
            i++
        }
        if(line.isNotEmpty())line = "{"+line+"}"
        return line
    }

}