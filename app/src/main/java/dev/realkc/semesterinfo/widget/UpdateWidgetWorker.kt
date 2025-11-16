package dev.realkc.semesterinfo.widget

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class UpdateWidgetWorker(
    val context: Context,
    val workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        // Fetch data or do some work and then update all instance of your widget
        SemesterWidget().updateAll(context)
        return Result.success()
    }
}
