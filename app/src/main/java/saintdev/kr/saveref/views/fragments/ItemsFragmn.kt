package saintdev.kr.saveref.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import org.json.JSONObject
import saintdev.kr.saveref.R
import saintdev.kr.saveref.models.libs.*
import saintdev.kr.saveref.models.network.HttpManager
import saintdev.kr.saveref.models.network.OnHttpResponse

class ItemsFragmn : Fragment() {
    private lateinit var rootView: View
    private lateinit var listView: ListView
    private val itemAdapter = FoodItemListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragmn_items, container, false)
        this.rootView = v
        this.listView = this.rootView.findViewById(R.id.food_in_items)

        this.listView.adapter = itemAdapter
        updateFoodList()

        return v
    }

    /**
     * FoodList 를 업데이트 합니다.
     */
    private fun updateFoodList() {
        val callback = object : OnHttpResponse {
            override fun onSuccess(json: JSONObject) {

                //  실제 데이터 업데이트 파싱 및 가져오기 부분
                val items = json.getJSONArray("items")

                for(i in 0 until items.length())
                    itemAdapter.addFood(idToFoodItem(items[i].toString().toInt(), resources))

                itemAdapter.notifyDataSetChanged()
            }

            override fun onFailed(ex: Exception?) {

                if(ex == null) {
                    // 통신 오류
                    "서버가 응답하지 않습니다.".openMessageDialog("오류", this@ItemsFragmn.activity)
                } else {
                    // 파싱 오류
                    "${ex.message}\n데이터 파싱에 실패 했습니다.".openMessageDialog("오류", this@ItemsFragmn.activity)
                }
            }
        }

        HttpManager(callback, AppEnv.PAGE03_URL).execute()

        // reset.
        itemAdapter.clearFood()
        itemAdapter.notifyDataSetChanged()
    }
}