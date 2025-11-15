package dev.realkc.semesterinfo

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo.WIDGET_CATEGORY_HOME_SCREEN
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.collection.intSetOf
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.setWidgetPreviews
import androidx.lifecycle.lifecycleScope
import dev.realkc.semesterinfo.ui.theme.SemesterInfoTheme
import dev.realkc.semesterinfo.widget.Receiver
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (peekAvailableContext() != null) {
            lifecycleScope.launch {
                updateWidgetPreview(peekAvailableContext()!!)
            }
        }

        setContent {
            SemesterInfoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
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
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SemesterInfoTheme {
        Greeting("Android")
    }
}