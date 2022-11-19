package com.suatzengin.workmanagersample

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Worker:
 *
 ** WorkRequest:
 *
 **{EN} It contains all of the information needed by WorkManager to schedule and run our work!
 *
 ** {TR} Work request work'ümüzün planlanması ve çalışması için WorkManager tarafından ihtiyaç duyulan tüm bilgileri içerir!
 * */

class ReminderWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {

        MakeNotification.showNotification(context = applicationContext)
        return Result.success()
    }

}