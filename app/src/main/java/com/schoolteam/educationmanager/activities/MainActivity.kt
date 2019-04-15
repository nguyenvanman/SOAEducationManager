package com.schoolteam.educationmanager.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.commons.Role.Admin
import com.schoolteam.educationmanager.commons.Role.Mod
import com.schoolteam.educationmanager.commons.Role.Parent
import com.schoolteam.educationmanager.commons.Role.Student
import com.schoolteam.educationmanager.commons.Role.Teacher
import com.schoolteam.educationmanager.commons.clearLoginInformation
import com.schoolteam.educationmanager.commons.getGroup
import com.schoolteam.educationmanager.fragments.NewsFragment
import com.schoolteam.educationmanager.fragments.NotificationFragment
import com.schoolteam.educationmanager.fragments.ScheduleFragment
import com.schoolteam.educationmanager.fragments.ScoreBoardFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.itemsSequence

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val notificationFragment = NotificationFragment()
    private val scheduleFragment = ScheduleFragment()
    private val scoreBoardFragment = ScoreBoardFragment()
    private val newsFragment = NewsFragment()

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
        checkRole()

    }

    private fun checkRole() {
        when (getGroup()) {
            Student -> {
                hideMenuItems(listOf(5), true)
            }
            Parent -> {
                hideMenuItems(listOf(5), true)
            }
            Teacher -> {
                hideMenuItems(listOf(3, 4), true)
            }
            Admin -> {
                hideMenuItems(listOf(3, 4, 5), true)
            }
            Mod -> {
                hideMenuItems(listOf(3, 4, 5), true)
            }
            else -> hideMenuItems(listOf(1, 2, 3, 4, 5), false)
        }
        displayFragment(newsFragment, R.string.drawer_menu_news)
        nav_view.menu.getItem(0).isChecked = true
    }

    private fun hideMenuItems(items: List<Int>, loginState: Boolean) {
        val menu = nav_view.menu
        for (item in menu.itemsSequence()) {
            item.isVisible = true
        }

        for (item in items) {
            menu.getItem(item).isVisible = false
        }

        menu.getItem(6).subMenu.getItem(0).isVisible = loginState
        menu.getItem(6).subMenu.getItem(1).isVisible = !loginState
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
            R.id.nav_news -> displayFragment(newsFragment, R.string.drawer_menu_news)
            R.id.nav_notification -> displayFragment(notificationFragment, R.string.drawer_menu_notification)
            R.id.nav_profile -> {
            }
            R.id.nav_schedule -> displayFragment(scheduleFragment, R.string.drawer_menu_scheduler)
            R.id.nav_score_board -> displayFragment(scoreBoardFragment, R.string.drawer_menu_score_board)
            R.id.nav_teaching_scheduler -> {
            }
            R.id.nav_logout -> doLogout()
            R.id.nav_login -> doLogin()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun doLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun doLogout() {
        clearLoginInformation()
        checkRole()
    }

    private fun displayFragment(fragment: Fragment, titleId: Int) {
        supportActionBar?.apply {
            title = getString(titleId)
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content_main, fragment)
            .addToBackStack("")
            .commit()
    }
}
