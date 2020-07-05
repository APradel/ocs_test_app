package com.example.ocstestapp.ui.programinfo

import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ocstestapp.R
import com.example.ocstestapp.api.ocs.OCSApiUtils
import com.example.ocstestapp.api.ocs.results.OCSApiSearchResult
import com.example.ocstestapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.program_info_fragment.view.*
import java.lang.ref.WeakReference


class ProgramInfoFragment : BaseFragment() {

    private lateinit var programInfoViewItem: ProgramInfoViewItem
    private lateinit var programInfoInitializerAsyncTask: ProgramInfoInitializerAsyncTask
    lateinit var detailLink: String
    var ocsApiSearchResult: OCSApiSearchResult? = null

    companion object {
        fun newInstance(detailLink: String?, ocsApiSearchResult: OCSApiSearchResult?)
                : ProgramInfoFragment
        {
            val fragment = ProgramInfoFragment()
            detailLink?.let {
                val args = Bundle()
                args.putString(OCS_API_DETAIL_LINK_KEY, detailLink)
                args.putSerializable(OCS_API_RESEARCH_RESULT_KEY, ocsApiSearchResult)
                fragment.arguments = args
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailLink = arguments?.getString(OCS_API_DETAIL_LINK_KEY) ?: throw NullPointerException("No program were founded")
        ocsApiSearchResult = arguments?.getSerializable(OCS_API_RESEARCH_RESULT_KEY) as OCSApiSearchResult
        programInfoInitializerAsyncTask = ProgramInfoInitializerAsyncTask(WeakReference(this))
        return inflater.inflate(R.layout.program_info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        programInfoInitializerAsyncTask.execute()
    }

    class ProgramInfoInitializerAsyncTask(private val programInfoWeakReference: WeakReference<ProgramInfoFragment>)
        : AsyncTask<Unit, Unit, Unit>()
    {
        override fun doInBackground(vararg p0: Unit?) {
            programInfoWeakReference.get()?.apply {
                val apiResponse = OCSApiUtils.getOCSApiDetail(detailLink)
                    ?: throw java.lang.NullPointerException("Detail couldn't be found")
                programInfoViewItem = ProgramInfoViewItem(apiResponse)
                if(null != ocsApiSearchResult)
                {
                    programInfoViewItem.title = ocsApiSearchResult?.title
                    programInfoViewItem.subtitle = ocsApiSearchResult?.subtitle
                    programInfoViewItem.imageUrl = ocsApiSearchResult?.fullScreenImageUrl
                        ?: ocsApiSearchResult?.imageUrl
                }
            }
        }

        override fun onPostExecute(result: Unit?) {
            programInfoWeakReference.get()?.apply {
                programInfoViewItem.retrieveData(true, Handler(Looper.getMainLooper())) {
                    //
                    view?.let {
                        it.program_info_image.setImageDrawable(programInfoViewItem.imageDrawable)
                        it.program_info_image_progress.visibility = View.GONE
                    }
                }
                view?.let {
                    it.program_info_title.text = programInfoViewItem.title
                    if (null != programInfoViewItem.subtitle) {
                        it.program_info_subtitle.text = programInfoViewItem.subtitle
                    }
                    it.program_info_pitch.text = programInfoViewItem.pitch
                }
            }
        }
    }
}
