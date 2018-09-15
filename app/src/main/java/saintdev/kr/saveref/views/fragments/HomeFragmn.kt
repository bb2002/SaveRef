package saintdev.kr.saveref.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragmn_home.*
import org.json.JSONObject
import saintdev.kr.saveref.R
import saintdev.kr.saveref.models.libs.*
import saintdev.kr.saveref.models.network.HttpManager
import saintdev.kr.saveref.models.network.OnHttpResponse

class HomeFragmn : Fragment() {
    private lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragmn_home, container, false)
        this.rootView = v
        updateData()

        return v
    }

    private fun updateData() {
        val callback = object : OnHttpResponse {
            override fun onSuccess(json: JSONObject) {

                //  실제 데이터 업데이트 파싱 및 가져오기 부분
                val item = Parser.page1Parse(json)
                if(item == null) {
                    "일부 데이터를 파싱 할 수 없습니다. 1".openMessageDialog("오류", this@HomeFragmn.activity)
                } else {
                    door_open_count_view.setText(item.doorOpenCount.toString() + "회")
                    val min = item.doorOpenTime / 60
                    val sec = item.doorOpenTime % 60
                    door_open_time_view.setText("$min 분 $sec 초")
                    door_open_power_view.setText("${Math.round(item.usedPower * 100) / 100.0}khw")
                    door_open_powerrate_view.setText("${item.usedPowerRate}원")
                    door_open_co2_view.setText("${item.exportedCo2}g")
                }

            }

            override fun onFailed(ex: Exception?) {

                if(ex == null) {
                    // 통신 오류
                    "서버가 응답하지 않습니다.".openMessageDialog("오류", this@HomeFragmn.activity)
                } else {
                    // 파싱 오류
                    "${ex.message}\n데이터 파싱에 실패 했습니다.".openMessageDialog("오류", this@HomeFragmn.activity)
                }
            }
        }


        HttpManager(callback, AppEnv.PAGE01_URL).execute()
    }
}