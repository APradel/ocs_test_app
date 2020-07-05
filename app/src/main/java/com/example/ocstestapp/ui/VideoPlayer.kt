package com.example.ocstestapp.ui

import android.content.Context
import android.net.Uri
import com.example.ocstestapp.R
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class VideoPlayer(val context: Context, playerView: PlayerView, uri: Uri?)
{
    constructor(context: Context, playerView: PlayerView, url: String)
            : this(context, playerView, Uri.parse(url))

    private var player: SimpleExoPlayer = SimpleExoPlayer.Builder(context).build()

    // Produces DataSource instances through which media data is loaded.
    private val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
        context, Util.getUserAgent(context, context.getString(R.string.app_name)))

    var uri : Uri? = uri
        set(value) { field = value; preparePlayer() }   //Update the player each times the Uri is changed

    init
    {
        // Bind the player to the view.
        playerView.player = player
        preparePlayer()
    }

    private fun preparePlayer()
    {
        uri?.let {
            val videoSource: MediaSource = DashMediaSource.Factory(dataSourceFactory)
                .createMediaSource(it)
            player.prepare(videoSource)
        }
    }

    fun start()
    {
        player.playWhenReady = true
    }

    fun pause()
    {
        player.playWhenReady = false
    }

    fun onDestroy()
    {
        player.release()
    }
}