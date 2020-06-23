package co.com.japl.iothome.specdb

import android.provider.BaseColumns

object DevicesSpecDB {
    object Device : BaseColumns{
        const val TABLE_NAME = "Device"
        const val COLUMN_NAME_ID = "_id"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_IMAGE = "image"
        const val COLUMN_NAME_LOCATION = "location"
    }
}