package com.boodabest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.boodabest.core.BaseActivity
import com.boodabest.ui.account.AccountOverviewFragment
import com.boodabest.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.app_toolbar))


        bottomNavigation.setOnNavigationItemSelectedListener {
            onBottomNavClick(it)
        }

        if (savedInstanceState == null) { // initial transaction should be wrapped like this
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitAllowingStateLoss()
        }
    }

    private fun onBottomNavClick(it: MenuItem): Boolean {
        when (it.itemId) {
            R.id.nav_item_home -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commit()
            }

            R.id.nav_item_cart -> {

            }

            R.id.nav_item_notification -> {

            }


            R.id.nav_item_account -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, AccountOverviewFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            }
        }

        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_home_menu_top, menu)
        return true
    }
}
