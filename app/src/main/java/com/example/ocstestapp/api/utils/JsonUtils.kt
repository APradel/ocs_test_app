package com.example.ocstestapp.api.utils

import org.json.JSONArray
import org.json.JSONObject

fun parseStringArray(jsonObject: JSONObject, key: String) : MutableList<String>?
{
    var res: MutableList<String>? = null
    if(false == jsonObject.isNull(key))
    {
        val jsonArray: JSONArray? = jsonObject.optJSONArray(key)
        jsonArray?.let {
            res = jsonArrayToStringArray(jsonArray)
        }
    }
    return res
}

fun jsonArrayToStringArray(jsonArray: JSONArray) : MutableList<String>
{
    val res: MutableList<String> = mutableListOf()
    for(i in 0 until jsonArray.length())
    {
        res.add( jsonArray.getString(i))
    }
    return res
}