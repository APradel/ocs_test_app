package com.example.ocstestapp.ui.programinfo

import com.example.ocstestapp.api.ocs.results.detail.OCSApiBaseDetail
import com.example.ocstestapp.ui.base.BaseApiResultViewItem


class ProgramInfoViewItem(data: OCSApiBaseDetail) : BaseApiResultViewItem(data)
{
    var pitch: String? = data.pitch

    init {
        imageUrl = data.fullScreenImageUrl
    }
}