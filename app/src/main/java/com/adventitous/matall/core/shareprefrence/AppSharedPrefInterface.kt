package com.adventitous.matall.core.shareprefrence

import com.adventitous.matall.core.datamodel.AppVersionInfo
import com.adventitous.matall.core.datamodel.UserInfo

interface AppSharedPrefInterface {

    fun resetUserInfo()
    fun setUserLoggedIn(isUserLoggedIn: Boolean)
    fun getUserLoggedIn(): Boolean
    fun setUserId(userId: Int)
    fun getUserId(): Int
    fun setUserName(userName: String)
    fun setUserMobileNumber(userMobileNumber: String)
    fun getUserMobileNumber(): String
    fun getUserInfo(): UserInfo
    fun setAppVersionInfo(appVersionInfo: AppVersionInfo)
    fun getAppVersionInfo(): AppVersionInfo
    fun setStartedScreenClicked(isClicked: Boolean)
    fun getStartedScreenClicked(): Boolean
    fun setEmail(email: String)
    fun getEmail(): String

}
