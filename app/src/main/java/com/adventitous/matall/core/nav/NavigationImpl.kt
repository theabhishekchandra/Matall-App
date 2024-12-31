package com.adventitous.matall.core.nav

import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import com.adventitous.matall.MainActivity
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class NavigationImpl @Inject constructor(
    @ActivityContext val context: Context
) : NavigationInterface {

    override fun getNavController(): NavController {
        return (context as MainActivity).getNavController()
    }

    override fun navigateTo(destinationId: Int) {
        getNavController().navigate(destinationId)
    }

    override fun navigateWithBundle(destinationId: Int, bundle: Bundle) {
        getNavController().navigate(destinationId, bundle)
    }

    override fun navigateUp(): Boolean {
        return getNavController().navigateUp()
    }

    override fun clearBackStack() {
        val navController = getNavController()
        navController.popBackStack(navController.graph.startDestinationId, false)
    }

    override fun popBackStack() {
        getNavController().popBackStack()
    }
}
