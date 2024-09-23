package com.example.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.broadcast.MyReceiver.Companion.EXTRA_COUNT

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ACTION_LOADED) {
                val percent = intent.getIntExtra(EXTRA_PERCENT, 0)
                progressBar.progress = percent
            }
        }
    }
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent(MyReceiver.ACTION_CLICKED).apply {
                putExtra(EXTRA_COUNT, ++counter)
            }
            sendBroadcast(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val intentFilter = IntentFilter().apply {
            addAction(ACTION_LOADED)
        }
        ContextCompat.registerReceiver(
            this,
            receiver,
            intentFilter,
            ContextCompat.RECEIVER_EXPORTED
        )
        startService(MyService.newIntent(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    companion object {
        const val ACTION_LOADED = "loaded"
        const val EXTRA_PERCENT = "percent"
    }
}