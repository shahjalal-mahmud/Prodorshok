package com.example.prodorshok.viewmodel.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class CheckMailViewModel : ViewModel() {

    var remainingTime by mutableStateOf(0)
        private set

    var isTimerRunning by mutableStateOf(false)
        private set

    private var timerJob: Job? = null

    fun startTimer(duration: Int = 60) {
        timerJob?.cancel()
        remainingTime = duration
        isTimerRunning = true

        timerJob = CoroutineScope(Dispatchers.Main).launch {
            while (remainingTime > 0) {
                delay(1000)
                remainingTime--
            }
            isTimerRunning = false
        }
    }

    fun openEmailApp(context: android.content.Context) {
        // existing logic for opening email
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
