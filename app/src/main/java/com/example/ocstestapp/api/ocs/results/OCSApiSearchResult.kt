package com.example.ocstestapp.api.ocs.results

import com.example.ocstestapp.api.ocs.JSON_KEY_PLAY_INFO_ID_HD
import com.example.ocstestapp.api.ocs.JSON_KEY_PLAY_INFO_ID_SD
import com.example.ocstestapp.api.ocs.JSON_KEY_PLAY_INFO_ID_UHD
import org.json.JSONObject
import java.io.Serializable

class PlayInfoId(jsonObject: JSONObject)
    : Serializable
{
    var hd: String? = jsonObject.optString(JSON_KEY_PLAY_INFO_ID_HD)
    var sd: String? = jsonObject.optString(JSON_KEY_PLAY_INFO_ID_SD)
    var uhd: String? = jsonObject.optString(JSON_KEY_PLAY_INFO_ID_UHD)
}

/**
 *  This class is intended to store all data from the JSON response
 *  from the API on search result
 */
class OCSApiSearchResult (apiResponse: JSONObject)
    : OCSApiBaseContent(apiResponse), Serializable
{
    var playInfoId: PlayInfoId = PlayInfoId(apiResponse)
}
