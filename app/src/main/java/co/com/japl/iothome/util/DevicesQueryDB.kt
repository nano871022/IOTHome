package co.com.japl.iothome.util

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import co.com.japl.iothome.def.IQueryDB
import co.com.japl.iothome.dto.DeviceDTO
import co.com.japl.iothome.querys.DeviceQuery
import co.com.japl.iothome.specdb.DevicesSpecDB
import co.com.japl.iothome.constants.DATABASE_NAME
import co.com.japl.iothome.constants.DATABASE_VERSION

class DevicesQueryDB (context:Context): SQLiteOpenHelper (context,DATABASE_NAME,null,DATABASE_VERSION) , IQueryDB<DeviceDTO>{
    companion object{
        val table = DevicesSpecDB.Device
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DeviceQuery.CREATE)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db,oldVersion,newVersion)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DeviceQuery.DROP)
        onCreate(db)
    }

    override fun insertRow(dto : DeviceDTO):Boolean{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(table.COLUMN_NAME_NAME,dto.name)
            put(table.COLUMN_NAME_LOCATION,dto.location)
            dto.image.let {
                put(table.COLUMN_NAME_IMAGE,it)
            }
        }
        val newRowId = db.insert(table.TABLE_NAME,null,values)
        if(newRowId != -1L){
            dto._id = newRowId
            return true
        }
        return false
    }

    override fun selectAll(): List<DeviceDTO>{
        try {
            var list = arrayListOf<DeviceDTO>()
            val db = this.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM ${table.TABLE_NAME}", null)

            with(cursor) {
                while (moveToNext()) {
                    var dto = DeviceDTO()
                    dto._id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                    dto.name =
                        getString(getColumnIndexOrThrow(table.COLUMN_NAME_NAME))
                    dto.location =
                        getString(getColumnIndexOrThrow(table.COLUMN_NAME_LOCATION))
                    dto.image =
                        getString(getColumnIndexOrThrow(table.COLUMN_NAME_IMAGE))
                    list.add(dto)
                }
            }
            return list
        }catch(e:SQLiteException){
            throw Exception(e.message)
        }
        return arrayListOf()
    }

    fun selectRow():List<DeviceDTO>{
        var list = arrayListOf<DeviceDTO>()
        val db = this.readableDatabase
        val projection = arrayOf(BaseColumns._ID,table.COLUMN_NAME_NAME,table.COLUMN_NAME_LOCATION,table.COLUMN_NAME_IMAGE)
        val selection = "${table.COLUMN_NAME_NAME} = ?"
        val selectionArgs = arrayOf("name1")
        val sorOrder = "${table.COLUMN_NAME_NAME} DESC"
        val cursor = db.query(table.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null)
        with(cursor){
            while (moveToNext()){
                var dto = DeviceDTO()
                dto._id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                dto.name = getString(getColumnIndexOrThrow(table.COLUMN_NAME_NAME))
                dto.location = getString(getColumnIndexOrThrow(table.COLUMN_NAME_LOCATION))
                dto.image = getString(getColumnIndexOrThrow(table.COLUMN_NAME_IMAGE))
            }
        }
        return list
    }

    override fun updateRow(dto:DeviceDTO):Boolean{
        val db = this.writableDatabase
        var values = ContentValues().apply {
            put(table.COLUMN_NAME_NAME,dto.name)
            put(table.COLUMN_NAME_IMAGE,dto.image)
            put(table.COLUMN_NAME_LOCATION,dto.location)
        }
        val selection = "${table.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(dto._id.toString())
        val count = db.update(table.TABLE_NAME,values,selection,selectionArgs)
        print("Cantidad de registros afectados "+count)
        return count > 0
    }

    override fun selectById(id: Long): DeviceDTO {
        var dto = DeviceDTO()
        val db  = this.writableDatabase
        val projection = arrayOf(BaseColumns._ID,table.COLUMN_NAME_NAME,table.COLUMN_NAME_LOCATION,table.COLUMN_NAME_IMAGE)
        val selection = "${table.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(id.toString())
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
                dto.name = getString(getColumnIndexOrThrow(table.COLUMN_NAME_NAME))
                dto.location = getString(getColumnIndexOrThrow(table.COLUMN_NAME_LOCATION))
                dto.image = getString(getColumnIndexOrThrow(table.COLUMN_NAME_IMAGE))
            }
        }
        return dto
    }

    override fun select(selection:String,args:Array<String>): DeviceDTO {
        var dto = DeviceDTO()
        val db  = this.writableDatabase
        val projection = arrayOf(BaseColumns._ID,table.COLUMN_NAME_NAME,table.COLUMN_NAME_LOCATION,table.COLUMN_NAME_IMAGE)
        val cursor = db.query(table.TABLE_NAME,
            projection,
            selection,
            args,
            null,
            null,
            null)
        with(cursor){
            while (moveToNext()){
                dto._id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                dto.name = getString(getColumnIndexOrThrow(table.COLUMN_NAME_NAME))
                dto.location = getString(getColumnIndexOrThrow(table.COLUMN_NAME_LOCATION))
                dto.image = getString(getColumnIndexOrThrow(table.COLUMN_NAME_IMAGE))
            }
        }
        return dto
    }
}
