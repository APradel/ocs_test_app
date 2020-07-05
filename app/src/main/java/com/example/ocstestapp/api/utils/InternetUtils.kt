package com.example.ocstestapp.api.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Looper
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLDecoder

/**
 *  This function:
 *      - can't be called from the main thread
 *   Return: retrieved data from an url as a String
 */
fun getStringFromUrl(urlPath: String) : String?
{
    if(Looper.myLooper() == Looper.getMainLooper())
    {
        throw IllegalThreadStateException("The OCSApiUtils::getSearchResults function " +
                "can't be called from the main thread")
    }
    var res: String? = null
    var connection: HttpURLConnection? = null
    var reader: BufferedReader? = null

    try {
        val url = URL(urlPath)
        connection = url.openConnection() as HttpURLConnection
        connection.connect()
        val stream: InputStream = connection.inputStream
        reader = BufferedReader(InputStreamReader(stream))
        val buffer = StringBuffer()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            line = URLDecoder.decode(line, "UTF-8")
            buffer.append("$line\n")
        }
        res = buffer.toString()
    } catch (e: MalformedURLException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        connection?.disconnect()
        try {
            reader?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return res
}

/**
 *  This function:
 *      - can't be called from the main thread
 *   Return: retrieved image from an url as a Bitmap
 */
fun getImageFromUrl(urlPath: String) : Bitmap?
{
    var res: Bitmap? = null
    try {
        val connection = URL(urlPath).openConnection() as HttpURLConnection
        connection.connect()
        val input = connection.inputStream
        res = BitmapFactory.decodeStream(input)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return res
}