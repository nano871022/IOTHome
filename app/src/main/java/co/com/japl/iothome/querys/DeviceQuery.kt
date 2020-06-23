package co.com.japl.iothome.querys

import android.provider.BaseColumns
import co.com.japl.iothome.specdb.ConnectionSpecDB
import co.com.japl.iothome.specdb.DevicesSpecDB

public class DeviceQuery{
    companion object {
        public const val DROP = "DROP TABLE IF EXISTS ${DevicesSpecDB.Device.TABLE_NAME}"
        public const val SELECT_ALL = "SELECT * FROM ${DevicesSpecDB.Device.TABLE_NAME}"
        public const val CREATE = "CREATE TABLE ${DevicesSpecDB.Device.TABLE_NAME}(" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${DevicesSpecDB.Device.COLUMN_NAME_NAME} TEXT," +
                "${DevicesSpecDB.Device.COLUMN_NAME_LOCATION} TEXT," +
                "${DevicesSpecDB.Device.COLUMN_NAME_IMAGE} TEXT" +
                ")"
    }

}