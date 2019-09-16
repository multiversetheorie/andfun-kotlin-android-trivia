/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        // Initialize drawerLayout variable
        drawerLayout = binding.drawerLayout

        // Link the NavController to ActionBar with NavigationUI.setupWithNavController
        // 1. Find NavController
        val navController = this.findNavController(R.id.myNavHostFragment)
        // 2. Link the NavController and the drawerLayout to ActionBar
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        // 2.5 Check if we have navigated away from the start destination. If yes, lock navigation drawer.
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        // 3. Set up a NavigationView for use with our navController
        NavigationUI.setupWithNavController(binding.navView, navController)

    }

    // Override onSupportNavigateUp to define what happens when we press Up
    override fun onSupportNavigateUp(): Boolean {
        // 1. Find NavController
        val navController = this.findNavController(R.id.myNavHostFragment)
        // 2. Call navigateUp on the navController
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}
