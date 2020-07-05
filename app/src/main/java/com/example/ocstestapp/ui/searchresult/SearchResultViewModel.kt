package com.example.ocstestapp.ui.searchresult

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ocstestapp.api.ocs.results.OCSApiSearchResult
import com.example.ocstestapp.api.ocs.OCSApiUtils
import org.json.JSONObject
import kotlin.concurrent.thread

class SearchResultViewModel : ViewModel() {
    val elements = MutableLiveData<List<SearchResultViewItem>>()
    val isReady = MutableLiveData<Boolean>(false)
    val count = MutableLiveData<Int>(0)
    val isValid = MutableLiveData<Boolean>(true)

    /**
     * This function:
     *      - is asynchronous
     *      - retrieve data from the query by using the OCS Api
     *      - update view items
     *  Parameters:
     *      - query: Any it have to have the query given by the user or the JSON given by the API
     *          (the JSON parameter is mainly used for test purpose)
     */
    private fun onNewSearch(query: Any)
    {
        val queryData = when(query){
            query as String -> query
            query as JSONObject -> query
            else -> throw TypeCastException("The query parameter given is incompatible")
        }
        thread {
            isReady.postValue(false)
            //This has to be done in another thread in order to not block the main thread
            val results: List<OCSApiSearchResult> = OCSApiUtils.getSearchResults(queryData) ?: mutableListOf()
            val viewItemList: MutableList<SearchResultViewItem> = mutableListOf()
            for(res in results)
            {
                val item = SearchResultViewItem(res)
                viewItemList.add(item)
            }
            elements.postValue(viewItemList)
            count.postValue(viewItemList.size)
            isValid.postValue(0 < viewItemList.size)
            isReady.postValue(true)
            for(item in viewItemList)
            {
                item.retrieveData(true)
            }
        }
    }

    fun onNewSearch(query: String) = onNewSearch(query as Any)
    fun onNewSearch(query: JSONObject) = onNewSearch(query as Any)
}
