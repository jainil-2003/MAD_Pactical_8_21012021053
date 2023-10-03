package com.example.mad_pactical_8_21012021053

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addAlarm : MaterialButton = findViewById(R.id.button)

        val card : MaterialCardView = findViewById(R.id.cardView2)

        card.visibility = View.GONE

        addAlarm.setOnClickListener {
            TimePickerDialog(this, {tp, hour, minute -> setAlarmTime(hour, minute)},Calendar.getInstance().get(Calendar.HOUR),Calendar.getInstance().get(Calendar.MINUTE),false).show()
            //TimePickerDialog(this, { tp, hour, minute -> sendDialogDataToActivity(hour, minute) }, Calendar.HOUR, Calendar.MINUTE, false).show()
            card.visibility = View.VISIBLE
        }

        val cancelAlarm : MaterialButton = findViewById(R.id.cancelAlarmbutton)
        cancelAlarm.setOnClickListener {
            stop()
            card.visibility = View.GONE
        }
    }
    fun setAlarmTime(hour : Int, minute : Int){
        val alarmTime = Calendar.getInstance()
        val year = alarmTime.get(Calendar.YEAR)
        val month = alarmTime.get(Calendar.MONTH)
        val date = alarmTime.get(Calendar.DATE)
        alarmTime.set(year, month, date, hour, minute, 0)
        val textAlarmTime : TextView = findViewById(R.id.tv4)
        textAlarmTime.text = SimpleDateFormat("hh:mm:ss a").format(alarmTime.time)
        setAlarm(alarmTime.timeInMillis, AlramBroadcastReciver.ALARM_START)
    }
    fun stop(){
        setAlarm(-1,AlramBroadcastReciver.ALARM_STOP)
    }
    @SuppressLint("NewApi", "ScheduleExactAlarm")
    fun setAlarm(millitime : Long, action : String) {
        val intentalarm = Intent(this, AlramBroadcastReciver::class.java)
        intentalarm.putExtra(AlramBroadcastReciver.ALARMKEY,action)
        val pendingintent = PendingIntent.getBroadcast(applicationContext,4356,intentalarm,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        if(action == AlramBroadcastReciver.ALARM_START){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,millitime,pendingintent)
        }
        else if(action == AlramBroadcastReciver.ALARM_STOP){
            alarmManager.cancel(pendingintent)
            sendBroadcast(intentalarm)
        }
    }
    private fun sendDialogDataToActivity(hour: Int, minute: Int){
        val textAlarmTime : TextView = findViewById(R.id.tv6)
        val alarmCalendar = Calendar.getInstance()
        val year: Int = alarmCalendar.get(Calendar.YEAR)
        val month: Int = alarmCalendar.get(Calendar.MONTH)
        val day: Int = alarmCalendar.get(Calendar.DATE)
        alarmCalendar.set(year, month, day, hour, minute, 0)
        textAlarmTime.text = SimpleDateFormat("hh:mm:ss a").format(alarmCalendar.time)
        setAlarm(alarmCalendar.timeInMillis,"Start")
        Toast.makeText(
            this,
            "Time: hours:${hour}, minutes:${minute}," +
                    "millis:${alarmCalendar.timeInMillis}",
            Toast.LENGTH_LONG
        ).show()
    }
}