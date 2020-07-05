package com.example.ocstestapp.ui.programinfo

import android.content.Intent
import android.os.Bundle
import com.example.ocstestapp.R
import com.example.ocstestapp.api.ocs.results.OCSApiSearchResult
import com.example.ocstestapp.ui.base.BaseActivity
import com.example.ocstestapp.utils.showDebugMessage

const val OCS_API_DETAIL_LINK_KEY = "detailLinkKey"
const val OCS_API_RESEARCH_RESULT_KEY = "researchResult"

class ProgramInfoActivity : BaseActivity()
{
    override val activityLayoutID: Int = R.layout.program_info_activity
    override val displayActionBar: Boolean = false

    var detailLink: String? = null
    var ocsApiSearchResult: OCSApiSearchResult? = null

    override fun launchMainFragment()
    {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ProgramInfoFragment.newInstance(detailLink, ocsApiSearchResult))
            .commitNow()
    }

    override fun handleIntent(intent: Intent) {
        super.handleIntent(intent)
        val extras: Bundle? = intent.extras
        extras?.let {
            detailLink = it.getString(OCS_API_DETAIL_LINK_KEY)
            ocsApiSearchResult = it.getSerializable(OCS_API_RESEARCH_RESULT_KEY) as OCSApiSearchResult
        }
    }
}
