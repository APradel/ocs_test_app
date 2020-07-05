package com.example.ocstestapp.ui.seasonslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ocstestapp.api.ocs.results.detail.OCSApiSeriesDetail

class SeasonsListViewModel : ViewModel() {
    val elements = MutableLiveData<List<SeasonsListViewItem>>()

    fun setSeriesDetail(ocsApiSeriesDetail: OCSApiSeriesDetail)
    {
        val seasonList = ArrayList<SeasonsListViewItem>()
        for(season in ocsApiSeriesDetail.seasons)
        {
            val viewItem = SeasonsListViewItem(season)
            seasonList.add(viewItem)
            viewItem.retrieveData(true)
        }
        elements.value = seasonList
    }
}
