package com.example.lab6video


import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var videoButton: Button
    private lateinit var radioButton: Button
    private lateinit var nextVideoButton: Button
    private lateinit var nextRadioButton: Button
    private var videoPlayer: MediaPlayer? = null
    private var radioPlayer: MediaPlayer? = null
    private var currentVideoIndex = 0
    private var currentRadioIndex = 0
    private val videoUrls = listOf(
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4"
    )

    private val radioUrls = listOf(
        "http://stream.whus.org:8000/whusfm",
        "https://stream.bigfm.de/hiphop/mp3-128/",
        "https://server.webnetradio.net/proxy/wsjf",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoView = findViewById(R.id.videoView)
        videoView.visibility = View.VISIBLE

        videoButton = findViewById(R.id.videoButton)
        radioButton = findViewById(R.id.radioButton)
        nextVideoButton = findViewById(R.id.nextVideoButton)
        nextRadioButton = findViewById(R.id.nextRadioButton) // Initialize nextRadioButton

        setVideoUri(videoUrls[currentVideoIndex])
        setRadioUri(radioUrls[currentRadioIndex])

        videoButton.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
                videoButton.text = "Video"
            } else {
                videoView.start()
                videoButton.text = "Pause"
            }
        }

        nextVideoButton.setOnClickListener {
            currentVideoIndex = (currentVideoIndex + 1) % videoUrls.size
            setVideoUri(videoUrls[currentVideoIndex])
        }

        radioButton.setOnClickListener {
            if (radioPlayer == null) {
                val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
                val result = audioManager.requestAudioFocus(
                    null,
                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN
                )

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    radioPlayer = MediaPlayer().apply {
                        setDataSource(radioUrls[currentRadioIndex])
                        prepare()
                        start()
                    }
                    radioButton.text =  "Radio"
                }
            } else {
                radioPlayer?.release()
                radioPlayer = null

                val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
                audioManager.abandonAudioFocus(null)

                radioButton.text = "Play"
            }
        }

        nextRadioButton.setOnClickListener {
            currentRadioIndex = (currentRadioIndex + 1) % radioUrls.size
            setRadioUri(radioUrls[currentRadioIndex])
        }
    }

    private fun setVideoUri(uri: String) {
        videoView.setVideoURI(Uri.parse(uri))
        videoView.start()
    }

    private fun setRadioUri(uri: String) {
        if (radioPlayer != null) {
            radioPlayer?.release()
            radioPlayer = null
            val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
            audioManager.abandonAudioFocus(null)
            radioButton.text = "Radio"
        }
        radioPlayer = MediaPlayer().apply {
            setDataSource(uri)
            prepare()
            start()
        }
        radioButton.text = "Radio"
    }
}

