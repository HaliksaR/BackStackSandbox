package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.myapplication.ui.main.MainFragment
import java.util.*

class MainActivity : AppCompatActivity() {

    private var counter: Int = 0

    private fun calcCounter(): String {
        val new = getBackStackAddEntries().size
        return if (counter == new) {
            "New $new : really $created"
        } else {
            counter = new
            new.toString()
        }
    }

    private val created: Int
        get() = getBackStackEntries().size

    private val fragments: Int
        get() = supportFragmentManager.fragments.size

    // adb shell setprop log.tag.FragmentManager DEBUG
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            add()
        }
        supportFragmentManager.addFragmentOnAttachListener { fm, fragment ->
            val operation = getBackStackEntries().last()
                .name?.substringAfterLast(':')

            val counter = calcCounter()
            Log.d("MainFragment", "$counter ______${operation}______")
            (fragment as? MainFragment)?.putArgs(counter, created, fragments)
        }
    }

    fun add() {
        val fragment = MainFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment, "${fragment::class}${counter}")
            .addToBackStack("${fragment::class}${counter}:add")
            .commit()
    }

    fun replace() {
        val fragment = MainFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, "${fragment::class}${counter}")
            .addToBackStack("${fragment::class}${counter}:replace")
            .commit()
    }

    fun pop() {
        Log.d("MainFragment", "______pop______")
        val backStackEntries = getBackStackAddEntries()
        supportFragmentManager.popBackStack(
            backStackEntries.last().name,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    private fun getBackStackAddEntries(): List<FragmentManager.BackStackEntry> =
        getBackStackEntries()
            .filter { it.name?.contains("replace") != true }

    private fun getBackStackEntries(): List<FragmentManager.BackStackEntry> {
        val list = mutableListOf<FragmentManager.BackStackEntry>()
        for (index in 0 until supportFragmentManager.backStackEntryCount) {
            list.add(supportFragmentManager.getBackStackEntryAt(index))
        }
        return list
    }
}