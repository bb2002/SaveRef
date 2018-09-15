package saintdev.kr.saveref

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ViewParent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragmn_items.*
import org.eazegraph.lib.models.ValueLinePoint
import org.eazegraph.lib.models.ValueLineSeries
import saintdev.kr.saveref.models.libs.MainActivityPagerAdapter



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add event listener
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        main_pager.addOnPageChangeListener(mOnViewPagerChangeListener)

        // set adapter
        main_pager.adapter = MainActivityPagerAdapter(supportFragmentManager)
        main_pager.currentItem = 0
    }

    override fun onStop() {
        super.onStop()
        main_pager.clearOnPageChangeListeners()
    }

    /**
     * Navigation button click listener
     */
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.navigation_home -> {
                main_pager.currentItem = 0
            }
            R.id.navigation_items -> {
                main_pager.currentItem = 2
            }
            R.id.navigation_status -> {
                main_pager.currentItem = 1
            }
        }
        true
    }

    private val mOnViewPagerChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {
            when(position) {
                0 -> navigation.selectedItemId = R.id.navigation_home
                1 -> navigation.selectedItemId = R.id.navigation_status
                2 -> navigation.selectedItemId = R.id.navigation_items
            }
        }
    }

}
