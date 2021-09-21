package com.hermanek.moviebrowserdemo.util

import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by jhermanek on 21.09.2021.
 */

class AppUtils {
    companion object {
        private const val API_FORMAT: String = "yyyy-MM-dd"

        fun convertToDateFormat(date: Date): String {
            return convertToDateFormatWithOffset(date, null)
        }

        fun convertToDateFormatWithOffset(date: Date, offset: Int?): String {
            val cal = Calendar.getInstance()
            cal.time = date
            if (offset != null) {
                cal.add(Calendar.DATE, offset)
            }

            val df = SimpleDateFormat(API_FORMAT, Locale.getDefault())
            return df.format(cal.time)
        }

        fun convertToFormat(input: String, format: String): String {
            val oldApiFormat = SimpleDateFormat(API_FORMAT, Locale.getDefault())
            val newFormat = SimpleDateFormat(format, Locale.getDefault())
            val date = oldApiFormat.parse(input)
            return newFormat.format(date)
        }
    }


}