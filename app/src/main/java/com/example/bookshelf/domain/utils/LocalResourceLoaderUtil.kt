package com.example.bookshelf.domain.utils

import com.example.bookshelf.BookShelfAppllication
import com.google.gson.GsonBuilder
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Modifier
import java.nio.charset.StandardCharsets

object LocalResourceLoaderUtil {

    suspend fun loadJSONFromAsset( filname: String?, className: Class<*>?): Any? {
        var json: String? = null
        return try {
            var `is`: InputStream? = null
            `is` =
                BookShelfAppllication.getApplicationContext().assets?.open(filname!!)
                    ?: filname?.let { BookShelfAppllication.getApplicationContext().getAssets()?.open(it) }
            val size = `is`?.available()
            val buffer = size?.let { ByteArray(it) }
            `is`?.read(buffer)
            `is`?.close()
            json =
                buffer?.let { String(it, StandardCharsets.UTF_8) }
            val gson = GsonBuilder().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.PROTECTED)
                .create()
            gson.fromJson(json, className)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }
}