package com.adventitous.matall.core.nav

import android.os.Bundle
import androidx.navigation.NavController

interface NavigationInterface {
    fun getNavController(): NavController
    fun navigateTo(destinationId: Int)
    fun navigateWithBundle(destinationId: Int, bundle: Bundle)
    fun navigateUp(): Boolean
    fun clearBackStack()
    fun popBackStack()
}
