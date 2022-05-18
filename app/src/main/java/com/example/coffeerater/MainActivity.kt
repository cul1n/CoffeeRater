package com.example.coffeerater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import fragments.CreateFragment
import fragments.DashboardFragment
import fragments.InfoFragment

class MainActivity : AppCompatActivity() {

    val coffeeShopModels: MutableList<CoffeeShopModel> = ArrayList()

    private val dashboardFragment = DashboardFragment()
    private val createFragment = CreateFragment()
    private val infoFragment = InfoFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(dashboardFragment)

        findViewById<BottomNavigationView>(R.id.bottom_navigation)
            .setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.ic_dashboard -> replaceFragment(dashboardFragment)
                    R.id.ic_create -> replaceFragment(createFragment)
                    R.id.ic_favorite -> replaceFragment(infoFragment)
                }
                true
            }
    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

}