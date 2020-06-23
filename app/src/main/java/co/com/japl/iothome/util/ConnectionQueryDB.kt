package co.com.japl.iothome.util

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.widget.Toast
import co.com.japl.iothome.def.IQueryDB
import co.com.japl.iothome.dto.ConnectionDTO
import co.com.japl.iothome.querys.ConnectionQuery
import co.com.japl.iothome.specdb.ConnectionSpecDB
import co.com.japl.iothome.constants.DATABASE_NAME
import co.com.japl.iothome.constants.DATABASE_VERSION
class ConnectionQueryDB (context:Context): SQLiteOpenHelper (context,DATABASE_NAME,null,DATABASE_VERSION) , IQueryDB<ConnectionDTO>{
    companion object{
        val table = ConnectionSpecDB.Connection
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(ConnectionQuery.CREATE)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db,oldVersion,newVersion)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(ConnectionQuery.DROP)
        onCreate(db)
    }

    override fun insertRow(connection : ConnectionDTO):Boolean{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(table.COLUMN_NAME_NAME,connection.name)
            put(table.COLUMN_NAME_EMAIL,connection.email)
            put(table.COLUMN_NAME_TOKEN,connection.token)
            put(table.COLUMN_NAME_IMAGE,connection.image)
        }
        val newRowId = db.insert(ConnectionSpecDB.Connection.TABLE_NAME,null,values)
        if(newRowId != -1L){
            connection._id = newRowId
            return true
        }
        return false
    }

    override fun selectAll(): List<ConnectionDTO>{
        var list = arrayListOf<ConnectionDTO>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${ConnectionSpecDB.Connection.TABLE_NAME}",null)

        with(cursor){
                while (moveToNext()) {
                    var dto = ConnectionDTO()
                    dto._id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                    dto.name =
                        getString(getColumnIndexOrThrow(ConnectionSpecDB.Connection.COLUMN_NAME_NAME))
                    dto.email =
                        getString(getColumnIndexOrThrow(ConnectionSpecDB.Connection.COLUMN_NAME_EMAIL))
                    dto.token =
                        getString(getColumnIndexOrThrow(ConnectionSpecDB.Connection.COLUMN_NAME_TOKEN))
                    dto.image =
                        getString(getColumnIndexOrThrow(ConnectionSpecDB.Connection.COLUMN_NAME_IMAGE))
                    list.add(dto)
            }
        }
        return list
    }

    fun selectRow():List<ConnectionDTO>{
        var list = arrayListOf<ConnectionDTO>()
        val db = this.readableDatabase
        val projection = arrayOf(BaseColumns._ID,ConnectionSpecDB.Connection.COLUMN_NAME_NAME,ConnectionSpecDB.Connection.COLUMN_NAME_EMAIL,ConnectionSpecDB.Connection.COLUMN_NAME_TOKEN,ConnectionSpecDB.Connection.COLUMN_NAME_IMAGE)
        val selection = "${ConnectionSpecDB.Connection.COLUMN_NAME_NAME} = ?"
        val selectionArgs = arrayOf("name1")
        val sorOrder = "${ConnectionSpecDB.Connection.COLUMN_NAME_NAME} DESC"
        val cursor = db.query(ConnectionSpecDB.Connection.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null)
        with(cursor){
            while (moveToNext()){
                var dto = ConnectionDTO()
                dto._id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                dto.name = getString(getColumnIndexOrThrow(ConnectionSpecDB.Connection.COLUMN_NAME_NAME))
                dto.email = getString(getColumnIndexOrThrow(ConnectionSpecDB.Connection.COLUMN_NAME_EMAIL))
                dto.token = getString(getColumnIndexOrThrow(ConnectionSpecDB.Connection.COLUMN_NAME_TOKEN))
                dto.image = getString(getColumnIndexOrThrow(ConnectionSpecDB.Connection.COLUMN_NAME_IMAGE))
            }
        }
        return list
    }

    override fun updateRow(dto:ConnectionDTO):Boolean{
        val db = this.writableDatabase
        var values = ContentValues().apply {
            put(ConnectionSpecDB.Connection.COLUMN_NAME_NAME,dto.name)
            put(ConnectionSpecDB.Connection.COLUMN_NAME_TOKEN,dto.token)
            put(ConnectionSpecDB.Connection.COLUMN_NAME_EMAIL,dto.email)
        }
        val selection = "${ConnectionSpecDB.Connection.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(dto._id.toString())
        val count = db.update(ConnectionSpecDB.Connection.TABLE_NAME,values,selection,selectionArgs)
        print("Cantidad de registros afectados "+count)
        return count > 0
    }

    override fun selectById(id: Long): ConnectionDTO {
        var dto = ConnectionDTO()
        val db = this.readableDatabase
        val projection = arrayOf(BaseColumns._ID,table.COLUMN_NAME_NAME,table.COLUMN_NAME_EMAIL,table.COLUMN_NAME_TOKEN,table.COLUMN_NAME_IMAGE)
        val selection = "${table.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        val sorOrder = "${table.COLUMN_NAME_NAME} DESC"
        val cursor = db.query(table.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null)
        with(cursor){
            while (moveToNext()){
                dto._id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                dto.name = getString(getColumnIndexOrThrow(ConnectionSpecDB.Connection.COLUMN_NAME_NAME))
                dto.email = getString(getColumnIndexOrThrow(ConnectionSpecDB.Connection.COLUMN_NAME_EMAIL))
                dto.token = getString(getColumnIndexOrThrow(ConnectionSpecDB.Connection.COLUMN_NAME_TOKEN))
                dto.image = getString(getColumnIndexOrThrow(ConnectionSpecDB.Connection.COLUMN_NAME_IMAGE))
            }
        }
        return dto
    }
}