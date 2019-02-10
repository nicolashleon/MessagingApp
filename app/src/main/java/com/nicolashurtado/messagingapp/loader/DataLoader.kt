package com.nicolashurtado.messagingapp.loader

import android.content.Context
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream

class DataLoader <T>(private val gson : Gson) {
    fun loadData(context : Context, fileName : String, c : Class<T>) : T? {
        var inputStream : InputStream? = null
        var json  : String? = try {
            inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer)

        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            inputStream?.let {
                it.close()
            }
        }

        return json?.let {
             gson.fromJson(it, c)
        }
    }
}