package dev.realkc.semesterinfo

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo.WIDGET_CATEGORY_HOME_SCREEN
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.collection.intSetOf
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.setWidgetPreviews
import androidx.lifecycle.lifecycleScope
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dev.realkc.semesterinfo.ui.composables.PinWidget
import dev.realkc.semesterinfo.ui.theme.SemesterInfoTheme
import dev.realkc.semesterinfo.widget.Receiver
import dev.realkc.semesterinfo.widget.UpdateWidgetWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        peekAvailableContext()?.let {
            lifecycleScope.launch {
                updateWidgetPreview(it)
            }
            scheduleWidgetUpdates(it)
        }

        setContent {
            SemesterInfoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainActivityContents(
                        resources = resources,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    suspend fun updateWidgetPreview(context: Context) {
        // Based on https://github.com/android/socialite/pull/129/files#r2001873677

        val installedProviders = context.getSystemService(AppWidgetManager::class.java)
            .installedProviders
        val providerInfo = installedProviders.firstOrNull {
            it.provider.className == Receiver::class.qualifiedName
        } ?: return // can also log here if provider isn't found
        if (providerInfo.generatedPreviewCategories == 0) {
            val glanceAppWidgetManager = GlanceAppWidgetManager(context)
            glanceAppWidgetManager.setWidgetPreviews<Receiver>(
                intSetOf(WIDGET_CATEGORY_HOME_SCREEN)
            )
        }
    }

    fun scheduleWidgetUpdates(context: Context) {
        val updateRequest = PeriodicWorkRequestBuilder<UpdateWidgetWorker>(12, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(context).enqueue(updateRequest)
    }
}

@Composable
fun MainActivityContents(resources: Resources,  modifier: Modifier = Modifier) {
    val week = weekInfo(resources, R.raw.msc)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = week.description,
            modifier = modifier

        )

        PinWidget()
    }

}