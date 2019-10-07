package com.example.applausegithubapp.data.converters

import androidx.room.TypeConverter
import java.util.Date

/**
 * author: Artur Godlewski
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = if (value == null) null else Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}