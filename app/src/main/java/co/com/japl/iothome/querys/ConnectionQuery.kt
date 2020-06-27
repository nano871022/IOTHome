package co.com.japl.iothome.querys

import android.provider.BaseColumns
import co.com.japl.iothome.specdb.ConnectionSpecDB
import co.com.japl.iothome.specdb.DevicesSpecDB

public class ConnectionQuery{
    companion object {
        public const val DROP = "DROP TABLE IF EXISTS ${ConnectionSpecDB.Connection.TABLE_NAME}"
        public const val SELECT_ALL = "SELECT * FROM ${ConnectionSpecDB.Connection.TABLE_NAME}"
        public const val CREATE = "CREATE TABLE ${ConnectionSpecDB.Connection.TABLE_NAME}(" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${ConnectionSpecDB.Connection.COLUMN_NAME_NAME} TEXT," +
                "${ConnectionSpecDB.Connection.COLUMN_NAME_EMAIL} TEXT," +
                "${ConnectionSpecDB.Connection.COLUMN_NAME_TOKEN} TEXT," +
                "${ConnectionSpecDB.Connection.COLUMN_NAME_IMAGE} TEXT" +
                ")"
    }

}