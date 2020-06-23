package co.com.japl.iothome.specdb

import android.provider.BaseColumns

object ConnectionSpecDB {
    object Connection : BaseColumns{
        const val TABLE_NAME = "Connection"
        const val COLUMN_NAME_ID = "_id"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_EMAIL = "email"
        const val COLUMN_NAME_TOKEN = "token"
        const val COLUMN_NAME_IMAGE = "image"
    }
}