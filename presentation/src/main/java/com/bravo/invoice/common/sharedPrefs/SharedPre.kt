package com.bravo.invoice.common.sharedPrefs

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveSelectedPosition(position: Int) {
        editor.putInt("selected_position", position)
        editor.apply()
    }

    fun getSelectedPosition(): Int {
        return sharedPreferences.getInt("selected_position", -1)
    }
}