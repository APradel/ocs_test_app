package com.example.ocstestapp.api.ocs.results

import com.example.ocstestapp.api.ocs.*
import com.example.ocstestapp.api.utils.parseStringArray
import org.json.JSONObject
import java.io.Serializable


/**
 *  This class represents data retrieved in all contents JSON object given by the api
 */
open class OCSApiBaseContent(jsonObject: JSONObject) : Serializable
{
    var id: String
    var title: String? = null
    var subtitle: String? = null
    var subtitleFocus: List<String>? = null
    var imageUrl: String? = null
    var fullScreenImageUrl: String? = null
    var detailLink: String? = null
    var duration: String? = null

    init {
        //  Try to retrieve the title value which is in a specific position such as:
        //  [{"color": null, "type": "text", "value": "GAME OF THRONES"}]
        val titleJsonObject: JSONObject? = jsonObject.optJSONArray(JSON_KEY_TITLE)?.get(0) as JSONObject?
        val titleValue :String? = titleJsonObject?.getString(JSON_KEY_TITLE_VALUE)

        id = jsonObject.getString(JSON_KEY_ID)
        title = titleValue
        subtitle = jsonObject.optString(JSON_KEY_SUBTITLE)
        subtitleFocus = parseStringArray(jsonObject, JSON_KEY_SUBTITLE_FOCUS)
        imageUrl = jsonObject.optString(JSON_KEY_IMAGE_URL)
        fullScreenImageUrl = jsonObject.optString(JSON_KEY_FULL_SCREEN_IMAGE_URL)
        detailLink = jsonObject.optString(JSON_KEY_ID_DETAIL_LINK)
        duration = jsonObject.optString(JSON_KEY_DURATION)
    }
}
