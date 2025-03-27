package com.ravimhzn.amp.environment

import android.content.Context
import android.util.Log
import com.ravimhzn.amp.util.JsonHandler
import com.ravimhzn.amp.util.LOG_ERROR
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EnvReader @Inject constructor(
    private val envFile: EnvFile,
    @ApplicationContext private val context: Context
) {

    val config: EnvConfig
        get() {
            val file = envFile.file()
            val jsonString = readAssetPackage(file.path) ?: ""

            val current = JsonHandler.fromJson(jsonString, EnvConfig::class.java) ?: run {
                Log.e(
                    LOG_ERROR,
                    "Unable to parse the environment json.\n" +
                            "File name:\t$file\n" +
                            "Json:\t$jsonString"

                )
                EnvConfig()
            }
            current.also {
                Log.e(LOG_ERROR, it.toString())
            }
            return current
        }

    private fun readAssetPackage(path: String): String? =
        try {
            context.assets.open(path).bufferedReader().use {
                it.readText()
            }
        } catch (e: Exception) {
            Log.e(LOG_ERROR, e.toString())
            ""
        }
}