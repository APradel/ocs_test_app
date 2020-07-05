package com.example.ocstestapp.ui.base

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import androidx.databinding.BaseObservable
import com.example.ocstestapp.api.ocs.IMAGE_URL_PREFIX
import com.example.ocstestapp.api.ocs.results.OCSApiBaseContent
import com.example.ocstestapp.api.utils.getImageFromUrl
import java.io.Serializable
import kotlin.concurrent.thread

open class BaseApiResultViewItem (var data: OCSApiBaseContent)
    : BaseObservable(), Serializable
{
    var imageDrawable: Drawable? = null
    var imageLoaded = false
    var title: String? = data.title
    var subtitle: String? = data.subtitle
    var imageUrl: String? = data.fullScreenImageUrl ?: data.imageUrl

    /**
     *  This function:
     *      -   could be asynchronous or not
     *      -   shall be used in order to get all data required
     *      -   use a thread instead of an AsyncTask in order to be called from any thread
     *      -   executes the callback when it's finished
     *  Parameters:
     *      -   async: Boolean  indicates if this function should be launch in another thread
     *      -   handler: Handler? optional, this handler allows to define on which thread the
     *      callback will be run
     *      -   callback: (b: Bitmap?) -> Unit optional, this lambda function is run at the end
     */
    fun retrieveData(async: Boolean = false, handler: Handler? = null, callback: () -> Unit = {})
    {
        fun task()
        {
            retrieveDataTask()
            handler?.post(callback) ?: callback()
        }
        if(async) thread { task() }
        else task()
    }

    /**
     *  This function acts like the doInBackground of an AsyncTask
     */
    protected open fun retrieveDataTask()
    {
        retrieveImage()
    }

    /**
     *  This function:
     *      -   stores the image inside the variable imageBitmap in order to prevent downloading
     *      it again if it's already done
     *      -   sets the imageLoaded variable to true when finished
     */
    private fun retrieveImage()
    {
        val selectedImageUrl: String? = imageUrl
        if(false == imageLoaded && null != selectedImageUrl)
        {
            val bitmap: Bitmap? = getImageFromUrl(
                IMAGE_URL_PREFIX + selectedImageUrl)
            val drawable: Drawable? = BitmapDrawable(Resources.getSystem(), bitmap)
            imageLoaded = true
            imageDrawable = drawable
            notifyChange()
        }
    }
}
