package com.schoolteam.educationmanager.activities

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.fragments.NotificationFragment
import com.schoolteam.educationmanager.fragments.ScheduleFragment
import com.schoolteam.educationmanager.fragments.ScoreBoardFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val notificationFragment = NotificationFragment()
    private val scheduleFragment = ScheduleFragment()
    private val scoreBoardFragment = ScoreBoardFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {}

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        displayFrament(notificationFragment)
        nav_view.menu.getItem(0).isChecked = true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_notification -> displayFrament(notificationFragment)
            R.id.nav_schedule -> displayFrament(scheduleFragment)
            R.id.nav_score_board -> displayFrament(scoreBoardFragment)
            R.id.nav_logout -> doLogout()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun doLogout() {

    }

    private fun displayFrament(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content_main, fragment)
            .addToBackStack("")
            .commit()
    }
}
