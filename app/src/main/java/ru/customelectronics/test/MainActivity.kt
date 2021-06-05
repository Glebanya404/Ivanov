package ru.customelectronics.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = javaClass.canonicalName

    private val hotFragment = HotFragment.newInstance()
    private val lastFragment = LastFragment.newInstance()
    private val topFragment = TopFragment.newInstance()
    private lateinit var viewPagerAdapter: ViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        tabLayout.setupWithViewPager(viewPager)
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(hotFragment, "hot")
        viewPagerAdapter.addFragment(lastFragment, "last")
        viewPagerAdapter.addFragment(topFragment, "top")
        viewPager.adapter = viewPagerAdapter
    }

    private class ViewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){
        val fragments = ArrayList<Fragment>()
        val fragmentsTitle = ArrayList<String>()

        fun addFragment(fragment: Fragment, title: String){
            fragments.add(fragment)
            fragmentsTitle.add(title)
        }

        override fun getCount(): Int = fragments.size

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getPageTitle(position: Int): String = fragmentsTitle[position]
    }
}