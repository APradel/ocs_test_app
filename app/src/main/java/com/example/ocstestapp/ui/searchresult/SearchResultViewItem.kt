package com.example.ocstestapp.ui.searchresult

import com.example.ocstestapp.api.ocs.results.OCSApiSearchResult
import com.example.ocstestapp.ui.base.BaseApiResultViewItem

class SearchResultViewItem (data: OCSApiSearchResult)
    : BaseApiResultViewItem(data)
{
    init {
        imageUrl = data.imageUrl
    }
}

