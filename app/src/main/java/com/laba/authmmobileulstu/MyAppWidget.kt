package com.laba.authmmobileulstu

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.laba.database.DanceStorage

/**
 * Implementation of App Widget functionality.
 */
class MyAppWidget : AppWidgetProvider() {
    val LOG_TAG = "myLogs"
    private var storage: DanceStorage? = null

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        super.onEnabled(context)
        Log.d(LOG_TAG, "onEnabled")
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        super.onDisabled(context);
        Log.d(LOG_TAG, "onDisabled");
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        if (storage == null) {
            storage= DanceStorage(context)
        }
        val widgetText = "бд: " + storage?.getElementCount()
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.my_app_widget)
        views.setTextViewText(R.id.appwidget_text, widgetText)

        Log.d(LOG_TAG, "updateWidget $appWidgetId")
        if (storage == null) {
            storage = DanceStorage(context)
        }

        val updateIntent = Intent(context, MyAppWidget::class.java)
        updateIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))
        val pIntent = PendingIntent.getBroadcast(context, appWidgetId, updateIntent, 0)
        views.setOnClickPendingIntent(R.id.text_view_id, pIntent)

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}