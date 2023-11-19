package com.dating.assignment.util

import android.content.Context

object UserPreferences {
        // Define the name of your preference file and the key for the user ID
        private const val PREF_NAME = "UserPreferences"
        private const val KEY_USER_ID = "user_id"

        fun saveUserId(context: Context, userId: String) {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            sharedPreferences.edit().putString(KEY_USER_ID, userId).apply()
        }

        fun getUserId(context: Context): String {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            // If the user ID is not found, return a default value (e.g., an empty string)
            return sharedPreferences.getString(KEY_USER_ID, "") ?: ""
        }
}