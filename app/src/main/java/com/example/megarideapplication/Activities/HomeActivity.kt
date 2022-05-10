package com.example.megarideapplication.Activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.megarideapplication.Fragments.*
import com.example.megarideapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var vpPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        vpPager = findViewById(R.id.viewpager)
        bottomNavigation = findViewById(R.id.bottom_navigation)

        val  adapterViewPager = MyPagerAdapter(supportFragmentManager)
        vpPager.adapter = adapterViewPager
        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item->

            when(item.itemId){
                R.id.ic_home ->{


                    vpPager.currentItem = 0
                    return@OnNavigationItemSelectedListener true
                }
                R.id.ic_availablerides ->{

                    vpPager.currentItem = 1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.ic_history ->{

                    vpPager.currentItem = 2
                    return@OnNavigationItemSelectedListener true
                }
                R.id.ic_profile ->{

                    vpPager.currentItem = 3
                    return@OnNavigationItemSelectedListener true
                }else -> vpPager.currentItem = 1
            }
            false
        }
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavigation.selectedItemId = R.id.ic_home
        vpPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            // Set Toolbar title and menu items
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        bottomNavigation.menu.getItem(0)
                    }
                    1 -> {
                        bottomNavigation.menu.getItem(1)
                    }
                    2 -> {
                        bottomNavigation.menu.getItem(2)
                    }
                    3 -> { bottomNavigation.menu.getItem(3)
                    }
                }
            }

            // While view pager settling on new item/page, check the correct bottom navigation view item
            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    val pos = vpPager.currentItem
                    bottomNavigation.menu.getItem(pos).isChecked = true
                }
            }

            // Not needed, must be implemented
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        })
    }



    class MyPagerAdapter(fragmentManager: FragmentManager?) :
        FragmentPagerAdapter(fragmentManager!!) {
        // Returns total number of pages
        override fun getCount(): Int {
            return NUM_ITEMS
        }

        // Returns the fragment to display for that page
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> Homefragment()
                1 ->Availableridesfragment()
                2 -> Historyfragment()
                3 -> Profilefragment()

                else -> {
                    Homefragment()
                }
            }
        }

        // Returns the page title for the top indicator

        companion object {
            private const val NUM_ITEMS = 4
        }
    }
}

