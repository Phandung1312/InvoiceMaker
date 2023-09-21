package com.bravo.domain.model.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.io.ByteArrayOutputStream

class BitmapConverter {
    @ToJson
    fun toJson(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        return android.util.Base64.encodeToString(byteArray, android.util.Base64.NO_WRAP)
    }

    @FromJson
    fun fromJson(base64String: String): Bitmap {
        val byteArray = android.util.Base64.decode(base64String, android.util.Base64.NO_WRAP)
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}