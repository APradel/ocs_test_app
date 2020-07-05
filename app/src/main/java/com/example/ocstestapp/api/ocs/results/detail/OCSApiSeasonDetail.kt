package com.example.ocstestapp.api.ocs.results.detail

import com.example.ocstestapp.api.ocs.JSON_KEY_SEASON_NUMBER
import org.json.JSONObject

/**
 * This class represents data retrieved from the API by using a detail link for a season
 */
class OCSApiSeasonDetail(jsonObject: JSONObject)
    : OCSApiProgramDetail(jsonObject)
{
    val number: Int = jsonObject.getInt(JSON_KEY_SEASON_NUMBER)
}
