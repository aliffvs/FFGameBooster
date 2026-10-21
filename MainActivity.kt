package com.example.ffgamebooster

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val gamePackages = listOf("com.dts.freefireth", "com.dts.freefiremax")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(28, 36, 28, 28)
            setBackgroundColor(Color.rgb(16, 22, 34))
        }
        layout.addView(TextView(this).apply {
            text = "FF GAME BOOSTER"
            textSize = 26f
            setTextColor(Color.rgb(80, 220, 150))
            gravity = Gravity.CENTER
        })
        layout.addView(TextView(this).apply {
            text = "Mode bermain • Tanpa Root"
            textSize = 15f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(0, 12, 0, 24)
        })
        addButton(layout, "BUKA FREE FIRE") { launchGame() }
        addButton(layout, "PENGATURAN BATERAI") {
            openSettings(Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS))
        }
        addButton(layout, "KELOLA APLIKASI FREE FIRE") { openGameSettings() }
        addButton(layout, "OPSI PENGEMBANG") {
            openSettings(Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS))
        }
        layout.addView(TextView(this).apply {
            text = "Tips sebelum bermain:\n• Tutup aplikasi yang tidak digunakan.\n• Aktifkan Jangan Ganggu jika diperlukan.\n• Sesuaikan grafis dengan kemampuan HP.\n\nAplikasi ini tidak mematikan proses lain secara otomatis dan tidak menjamin kenaikan FPS."
            textSize = 14f
            setTextColor(Color.LTGRAY)
            setPadding(0, 24, 0, 0)
        })
        setContentView(layout)
    }

    private fun addButton(parent: LinearLayout, label: String, action: () -> Unit) {
        val button = Button(this).apply {
            text = label
            isAllCaps = false
            setOnClickListener { action() }
        }
        parent.addView(button, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply { bottomMargin = 10 })
    }

    private fun launchGame() {
        for (pkg in gamePackages) {
            val intent = packageManager.getLaunchIntentForPackage(pkg)
            if (intent != null) {
                startActivity(intent)
                return
            }
        }
        Toast.makeText(this, "Free Fire/Free Fire MAX tidak ditemukan.", Toast.LENGTH_LONG).show()
    }

    private fun openGameSettings() {
        val pkg = gamePackages.firstOrNull { packageManager.getLaunchIntentForPackage(it) != null }
        if (pkg == null) {
            Toast.makeText(this, "Free Fire tidak ditemukan.", Toast.LENGTH_SHORT).show()
            return
        }
        openSettings(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$pkg")))
    }

    private fun openSettings(intent: Intent) {
        try {
            startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(this, "Pengaturan ini tidak tersedia di perangkatmu.", Toast.LENGTH_SHORT).show()
        }
    }
}
