package com.hermanek.moviebrowserdemo.util

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by jhermanek on 21.09.2021.
 */

class AppUtils {
    companion object : SupportedLanguages {
        private const val API_FORMAT: String = "yyyy-MM-dd"

        fun convertToFormat(input: String, format: String): String {
            val oldApiFormat = SimpleDateFormat(API_FORMAT, Locale.getDefault())
            val newFormat = SimpleDateFormat(format, Locale.getDefault())
            val date = oldApiFormat.parse(input)
            return newFormat.format(date)
        }

        fun getSupportedLanguage(): String {
            return when (ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0].toLanguageTag()) {
                czech -> {
                    czech
                }
                english -> {
                    english
                }
                else -> {
                    default
                }
            }
        }
    }


}