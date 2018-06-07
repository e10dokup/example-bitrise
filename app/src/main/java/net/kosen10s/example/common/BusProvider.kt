package net.kosen10s.example.common

import android.os.Handler
import android.os.Looper
import com.squareup.otto.Bus


enum class BusProvider {
    INSTANCE;

    private val bus = MainThreadBus()

    /**
     * [Bus] that always post on Main Thread
     */
    private class MainThreadBus : Bus() {
        private val handler = Handler(Looper.getMainLooper())

        override fun post(event: Any) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                super.post(event)
            } else {
                handler.post({ super.post(event) })
            }
        }
    }
}