package com.example.ocstestapp.api.ocs.results.detail

import com.example.ocstestapp.api.ocs.JSON_KEY_SEASONS
import org.json.JSONObject


class OCSApiSeriesDetail(jsonObject: JSONObject) : OCSApiBaseDetail(jsonObject)
{
    val seasons: ArrayList<OCSApiSeasonDetail> = ArrayList()

    init {
        val seasonsArray = jsonObject.optJSONArray(JSON_KEY_SEASONS)
        seasonsArray?.let {
            for (i in 0 until seasonsArray.length())
            {
                val season = OCSApiSeasonDetail(seasonsArray.getJSONObject(i))
                seasons.add(season)
            }
            pitch = seasons[0].pitch
        }
    }
}