package com.adventitous.matall.core.shareprefrence

import android.content.Context
import android.content.SharedPreferences
import com.adventitous.matall.core.datamodel.AppVersionInfo
import com.adventitous.matall.core.datamodel.CoreConstants
import com.adventitous.matall.core.datamodel.UserInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppSharedPref @Inject constructor(@ApplicationContext val context: Context) : AppSharedPrefInterface {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        CoreConstants.SHARED_PREFERENCE_DB,
        Context.MODE_PRIVATE
    )
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun resetUserInfo() {
        editor.clear().apply()
    }

    override fun setUserLoggedIn(isUserLoggedIn: Boolean) {
        editor.putBoolean("user_logged_in", isUserLoggedIn).apply()
    }

    override fun getUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("user_logged_in", false)
    }


    override fun setUserId(userId: Int) {
        editor.putInt("user_id", userId).apply()
    }
    override fun getUserId(): Int {
        return sharedPreferences.getInt("user_id", 0)
    }

    override fun setUserName(userName: String) {
        editor.putString("user_name", userName).apply()
    }

    override fun setUserMobileNumber(userMobileNumber: String) {
        editor.putString("user_mobile_number", userMobileNumber).apply()
    }

    override fun getUserMobileNumber(): String {
        return sharedPreferences.getString("user_mobile_number", "") ?: ""
    }

    override fun getUserInfo(): UserInfo {
        val userId = getUserId()
        val userName = sharedPreferences.getString("user_name", "") ?: ""
        val userMobileNumber = sharedPreferences.getString("user_mobile_number", "") ?: ""
        return UserInfo(userId, userName, userMobileNumber)
    }


    override fun setAppVersionInfo(appVersionInfo: AppVersionInfo) {
        editor.putString("app_version_name", appVersionInfo.versionName).apply()
        editor.putInt("app_version_code", appVersionInfo.versionCode).apply()
    }

    override fun getAppVersionInfo(): AppVersionInfo {
        val versionName = sharedPreferences.getString("app_version_name", "") ?: ""
        val versionCode = sharedPreferences.getInt("app_version_code", 0)
        return AppVersionInfo(versionName, versionCode)
    }


    override fun setStartedScreenClicked(isClicked: Boolean) {
        editor.putBoolean("started_screen_clicked", isClicked).apply()
    }

    override fun getStartedScreenClicked(): Boolean {
        return sharedPreferences.getBoolean("started_screen_clicked", false)
    }

    override fun setEmail(email: String) {
        editor.putString("user_email", email).apply()
    }

    override fun getEmail(): String {
        return sharedPreferences.getString("user_email", "") ?: ""
    }
}