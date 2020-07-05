package com.example.ocstestapp.api.ocs.results.detail

import com.example.ocstestapp.api.ocs.JSON_KEY_SEASONS
import org.json.JSONObject


abstract class OCSApiDetailFactory
{
    companion object {
        fun create(jsonObject: JSONObject) : OCSApiBaseDetail
        {
            if(jsonObject.has(JSON_KEY_SEASONS))
            {
                return OCSApiSeriesDetail(jsonObject)
            }
            return OCSApiProgramDetail(jsonObject)
        }
    }
}