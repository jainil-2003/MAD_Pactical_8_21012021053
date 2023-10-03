package com.example.mad_pactical_8_21012021053

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class AlramService : Service() {
    lateinit var player:MediaPlayer

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onDestroy() {
        player.stop()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent!= null){
            player=MediaPlayer.create(this,R.raw.alarm)
            player.start()
        }
        return START_STICKY
    }
}