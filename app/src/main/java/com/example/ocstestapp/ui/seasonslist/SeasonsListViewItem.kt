package com.example.ocstestapp.ui.seasonslist

import com.example.ocstestapp.api.ocs.results.OCSApiSearchResult
import com.example.ocstestapp.api.ocs.results.detail.OCSApiSeasonDetail
import com.example.ocstestapp.ui.base.BaseApiResultViewItem

class SeasonsListViewItem (data: OCSApiSeasonDetail)
    : BaseApiResultViewItem(data)
{
    var pitch = data.pitch
    var number = data.number

    init {
        imageUrl = data.imageUrl
    }
}

