package com.example.ocstestapp.api.ocs

import android.os.Looper
import com.example.ocstestapp.api.ocs.results.OCSApiSearchResult
import com.example.ocstestapp.api.ocs.results.detail.OCSApiBaseDetail
import com.example.ocstestapp.api.ocs.results.detail.OCSApiDetailFactory
import com.example.ocstestapp.api.utils.getStringFromUrl
import org.json.JSONArray
import org.json.JSONObject

abstract class OCSApiUtils {

    companion object {

        /**
         * This function:
         *      - retrieve data from the api
         *      - can't be called on the main thread
         *      - return: List<OCSApiResearchResult>
         */
        fun getSearchResults(query: String): List<OCSApiSearchResult>?
        {
            if(Looper.myLooper() == Looper.getMainLooper())
            {
                throw IllegalThreadStateException("The OCSApiUtils::getSearchResults function " +
                        "can't be called from the main thread")
            }
            var res: List<OCSApiSearchResult>? = null
            val apiResponse: String? =
                getStringFromUrl(QUERY_PREFIX + query)
            apiResponse?.let {
                res = getSearchResults(JSONObject(apiResponse))
            }
            return res
        }

        /**
         *  This function:
         *      - can't be called on the main thread
         *  Parameter:
         *      - jsonObject: JSONObject represent the JSON object produce by the OCS Api for a search query
         *  Return: List<OCSApiResearchResult>
         */
        fun getSearchResults(jsonObject: JSONObject?): List<OCSApiSearchResult> {
            val res: MutableList<OCSApiSearchResult> = mutableListOf()
            if(jsonObject?.has(RESULTS_ARRAY_KEY) == true)
            {
                val content: JSONArray? = jsonObject.optJSONArray(RESULTS_ARRAY_KEY)
                content?.let {
                    for (i in 0 until content.length()) {
                        val resultItem: JSONObject = content[i] as JSONObject
                        resultItem.let {
                            res.add(
                                OCSApiSearchResult(
                                    resultItem
                                )
                            )
                        }
                    }
                }
            }
            return res
        }


        /**
         *  This function:
         *      - can't be called on the main thread
         *      - gets all data required from the AOI
         *      - parses those data
         *  Parameter:
         *      - detailLink: String
         *  Return: OCSApiBaseDetail
         */
        fun getOCSApiDetail(detailLink: String) : OCSApiBaseDetail?
        {
            var res: OCSApiBaseDetail? = null
            val apiResponse = getStringFromUrl(DETAIL_URL_PREFIX + detailLink)
            apiResponse?.let {
                val jsonObject = JSONObject(apiResponse)
                res = OCSApiDetailFactory.create(jsonObject.getJSONObject(JSON_KEY_CONTENTS))
            }
            return res
        }

    }

}