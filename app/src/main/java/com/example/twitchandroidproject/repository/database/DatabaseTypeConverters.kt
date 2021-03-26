package com.example.twitchandroidproject.repository.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.example.twitchandroidproject.repository.model.UserProfile
import java.io.ByteArrayOutputStream
import java.util.Date

class DatabaseTypeConverters {

    companion object {
        const val LIST_OF_STRINGS_SEPARATOR = ";"
    }

    // List of Strings <-> String

    @TypeConverter
    fun fromListOfStringsToString(list: List<String>?): String? {
        return list?.joinToString(LIST_OF_STRINGS_SEPARATOR)
    }

    @TypeConverter
    fun fromStringToListOfStrings(value: String?): List<String>? {
        return when {
            value == null -> null
            value.isEmpty() -> listOf()
            else -> value.split(LIST_OF_STRINGS_SEPARATOR)
        }
    }

    // String <-> UserProfileType

    @TypeConverter
    fun fromStringToUserProfileType(value: String?): UserProfile.UserProfileType? {
        return if (value == null) null else UserProfile.UserProfileType.valueOf(value)
    }

    @TypeConverter
    fun fromUserProfileTypeToString(userProfileType: UserProfile.UserProfileType?): String? {
        return userProfileType?.toString()
    }

    // Date <-> Timestamp

    @TypeConverter
    fun fromTimestampToDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun fromDateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    // Bitmap <-> ByteArray

    @TypeConverter
    fun fromByteArrayToBitmap(data: ByteArray?): Bitmap? {
        return if (data != null) {
            BitmapFactory.decodeByteArray(data, 0, data.size)
        } else {
            null
        }
    }

    @TypeConverter
    fun fromBitmapToByteArray(bitmap: Bitmap?): ByteArray? {
        return if (bitmap != null) {
            val byteArrayStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayStream)
            byteArrayStream.toByteArray()
        } else {
            null
        }
    }
}