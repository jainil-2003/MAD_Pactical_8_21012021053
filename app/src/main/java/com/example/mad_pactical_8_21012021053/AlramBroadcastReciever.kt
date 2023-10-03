package com.example.mad_pactical_8_21012021053

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlramBroadcastReciver : BroadcastReceiver() {
    companion object{
        val ALARMKEY="alarmkey"
        val ALARM_STOP="alarmstop"
        val ALARM_START="alarmstart"
    }
    override fun onReceive(context: Context, intent: Intent) {
        val data = intent.getStringExtra(ALARMKEY)
        val intentService = Intent(context,AlramService::class.java)
        if(data == ALARM_START){

            context.startService(intentService)
        }
        else if(data == ALARM_STOP){
            context.stopService(intentService)
        }

    }
}