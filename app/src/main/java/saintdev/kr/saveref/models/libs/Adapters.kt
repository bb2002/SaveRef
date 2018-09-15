package saintdev.kr.saveref.models.libs

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import saintdev.kr.saveref.R
import saintdev.kr.saveref.views.fragments.HomeFragmn
import saintdev.kr.saveref.views.fragments.StatusFragmn
import saintdev.kr.saveref.views.fragments.ItemsFragmn

/**
 * MainActivity 의 ViewPager 부분
 */
class MainActivityPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val fragments = arrayOf(HomeFragmn(), StatusFragmn(), ItemsFragmn())

    override fun getItem(pos: Int)  = fragments[pos]

    override fun getCount(): Int = fragments.size
}

/**
 * Items 의 ItemAdapter 부분
 */
class FoodItemListAdapter() : BaseAdapter() {
    private val items = arrayListOf<FoodItem>()

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {
        val view =
            if(convertView == null) {
                val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(R.layout.csv_food_items, parent, false)
            } else convertView

        val iconView = view.findViewById<ImageView>(R.id.csv_item_icon)
        val nameView = view.findViewById<TextView>(R.id.csv_item_title)
        val item = items[pos]

        iconView.setImageBitmap(item.foodIcon)
        nameView.text = item.foodName

        return view
    }

    override fun getItem(p0: Int) = items[p0]

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getCount() = items.size

    /**
     * 새로운 음식을 추가한다.
     */
    fun addFood(food: FoodItem) = this.items.add(food)

    /**
     * 음식 목록을 초기화 한다.
     */
    fun clearFood() {
        this.items.clear()
        notifyDataSetChanged()
    }

}