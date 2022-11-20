package com.suatzengin.workmanagersample

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 ** WorkRequest:
 *
 **----{EN} It contains all of the information needed by WorkManager to schedule and run our work!
 *
 **----{TR} Work request work'ümüzün planlanması ve çalışması için WorkManager tarafından ihtiyaç duyulan tüm bilgileri içerir!
 * */

class ReminderWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    companion object{
        const val KEY_OUTPUT_TEXT = "OUTPUT_TEXT"
    }
    override fun doWork(): Result {
        val title = inputData.getString("KEY_TITLE")

        MakeNotification.showNotification(context = applicationContext, title ?: "Title")

        val data: Data = Data.Builder()
            .putString(KEY_OUTPUT_TEXT, "Work finished!")
            .build()
        //val data = workDataOf(KEY_OUTPUT_TEXT to "Work Finished!")

        return Result.success(data)
    }

}