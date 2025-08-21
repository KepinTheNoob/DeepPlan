package com.example.deepplan.ui.screen.projectDashboardScreen

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.deepplan.data.Task

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun GanttChartComposable(tasks: List<Task>, modifier: Modifier = Modifier) {
    val htmlContent = generateGanttHtml(tasks)

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            // The factory block is where you create and initialize the View.
            // This block is called only once.
            WebView(context).apply {
                settings.javaScriptEnabled = true
                // These settings are important for zoom and overview functionality
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
                settings.builtInZoomControls = true
                settings.displayZoomControls = false // Hides the on-screen zoom buttons
            }
        },
        update = { webView ->
            // The update block is called when the composable is recomposed.
            // We load the HTML content here to ensure the chart updates if the task list changes.
            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
        }
    )
}

private fun generateGanttHtml(tasks: List<Task>): String {
    // Convert the Kotlin Task list to a JavaScript-compatible JSON array string
    val tasksJson = tasks.joinToString(separator = ",\n", prefix = "[\n", postfix = "\n]") {
        """
        {
            id: '${it.id}',
            name: '${it.name}',
            start: '${it.start}',
            end: '${it.end}',
            dependencies: '${it.dependencies}'
        }
        """.trimIndent()
    }

    // The complete HTML structure
    return """
    <!DOCTYPE html>
    <html>
    <head>
        <!-- CHANGE 1: The viewport meta tag is updated to allow user scaling -->
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gantt Chart</title>
        <!-- Frappe Gantt CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/frappe-gantt@0.6.1/dist/frappe-gantt.css">
        <style>
            body {
                font-family: sans-serif;
                background: #f8f9fa;
                margin: 0;
                padding: 15px;
                -webkit-user-select: none; /* Disable text selection for a cleaner feel */
                user-select: none;
            }
            .gantt .bar-label {
                font-size: 14px;
                font-weight: bold;
            }
            .gantt .grid-header {
                background: #e9ecef;
            }
            /* CHANGE 2: The restrictive width: 100% rule for the SVG is removed.
               This allows the SVG to expand to its natural width, creating overflow to scroll. */
        </style>
    </head>
    <body>
        <div id="gantt"></div>

        <!-- Frappe Gantt JS -->
        <script src="https://cdn.jsdelivr.net/npm/frappe-gantt@0.6.1/dist/frappe-gantt.min.js"></script>

        <script>
            var tasks = $tasksJson;
            var gantt = new Gantt("#gantt", tasks, {
                header_height: 60,
                bar_height: 25,
                bar_corner_radius: 5,
                padding: 20,
                view_mode: 'Week' // Options: 'Quarter Day', 'Half Day', 'Day', 'Week', 'Month'
            });
        </script>
    </body>
    </html>
    """.trimIndent()
}