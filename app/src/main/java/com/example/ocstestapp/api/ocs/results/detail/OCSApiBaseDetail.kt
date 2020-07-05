package com.example.ocstestapp.api.ocs.results.detail

import com.example.ocstestapp.api.ocs.JSON_KEY_PITCH
import com.example.ocstestapp.api.ocs.RESULTS_ARRAY_KEY
import com.example.ocstestapp.api.ocs.results.OCSApiBaseContent
import com.example.ocstestapp.api.ocs.results.OCSApiSearchResult
import org.json.JSONObject


/**
 * This abstract class represents data retrieved from the API by using a detail link
 */
abstract class OCSApiBaseDetail(jsonObject: JSONObject)
    : OCSApiBaseContent(jsonObject)
{
    var pitch: String? = jsonObject.optString(JSON_KEY_PITCH)
}