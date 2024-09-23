package com.example.broadcast

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.broadcast.MainActivity.Companion.ACTION_LOADED
import com.example.broadcast.MainActivity.Companion.EXTRA_PERCENT
import kotlin.concurrent.thread

class MyService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread {
            for (i in 1 .. 10) {
                Thread.sleep(1000)
                Intent(ACTION_LOADED).apply {
                    putExtra(EXTRA_PERCENT, i * 10)
                    sendBroadcast(this)
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, MyService::class.java)
    }
}