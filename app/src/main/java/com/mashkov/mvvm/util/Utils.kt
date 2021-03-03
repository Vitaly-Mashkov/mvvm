package com.mashkov.mvvm.util

import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun navigate(destination: NavDirections, findNavController: NavController) =
    with(findNavController) {
        currentDestination?.getAction(destination.actionId)?.let { navigate(destination) }
    }

