package com.schoolteam.educationmanager.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.bumptech.glide.Glide
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.commons.*
import com.schoolteam.educationmanager.commons.Role.Admin
import com.schoolteam.educationmanager.commons.Role.Mod
import com.schoolteam.educationmanager.commons.Role.Parent
import com.schoolteam.educationmanager.commons.Role.Student
import com.schoolteam.educationmanager.commons.Role.Teacher
import com.schoolteam.educationmanager.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.itemsSequence
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val notificationFragment = NotificationFragment()
    private val scheduleFragment = ScheduleFragment()
    private val scoreBoardFragment = ScoreBoardFragment()
    private val newsFragment = NewsFragment()
    private val teachingScheduleFragment = TeachingScheduleFragment()
    private val studentsInfoFragment = ChildrenInfoFragment()
    private var isDoubleClickedBack = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        checkRole()
        showUserInfo()
    }

    private fun showUserInfo() {
        val headerView = nav_view.getHeaderView(0)
        Glide.with(this).load(getUserAvatarUrl()).into(headerView.findViewById(R.id.imgAvatar))
        headerView.findViewById<TextView>(R.id.tvName).text = getCurrentUserName()
    }

    private fun checkRole() {
        when (getGroup()) {
            Student -> {
                hideMenuItems(listOf(5, 6), true)
            }
            Parent -> {
                hideMenuItems(listOf(3, 4, 5), true)
            }
            Teacher -> {
                hideMenuItems(listOf(3, 4, 6), true)
            }
            Admin -> {
                hideMenuItems(listOf(3, 4, 5, 6), true)
            }
            Mod -> {
                hideMenuItems(listOf(3, 4, 5, 6), true)
            }
            else -> hideMenuItems(listOf(1, 2, 3, 4, 5, 6), false)
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

        menu.getItem(7).subMenu.getItem(0).isVisible = loginState
        menu.getItem(7).subMenu.getItem(1).isVisible = !loginState
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (isDoubleClickedBack) {
                finish()
                return
            }

            isDoubleClickedBack = true;
            toast(R.string.press_back_to_exit)
            Handler().postDelayed({ isDoubleClickedBack = false }, 2000)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return isLogin()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_change_password -> {
                startActivity(Intent(this, ChangePasswordActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_news -> displayFragment(newsFragment, R.string.drawer_menu_news)
            R.id.nav_notification -> displayFragment(notificationFragment, R.string.drawer_menu_notification)
            R.id.nav_profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
            }
            R.id.nav_schedule -> {
                scheduleFragment.isStudentMode = getGroup() == Role.Student
                displayFragment(scheduleFragment, R.string.drawer_menu_scheduler)
            }
            R.id.nav_score_board -> {
                scoreBoardFragment.isStudentMode = getGroup() == Role.Student
                displayFragment(scoreBoardFragment, R.string.drawer_menu_score_board)
            }
            R.id.nav_teaching_scheduler -> displayFragment(
                teachingScheduleFragment,
                R.string.drawer_menu_teaching_scheduler
            )

            R.id.nav_students_info -> {
                displayFragment(studentsInfoFragment, R.string.drawer_menu_children_info)
            }
            R.id.nav_logout -> doLogout()
            R.id.nav_login -> doLogin()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun doLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun doLogout() {
        clearLoginInformation()
        checkRole()
        showUserInfo()
        toast(R.string.logout_success)
        invalidateOptionsMenu()
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
