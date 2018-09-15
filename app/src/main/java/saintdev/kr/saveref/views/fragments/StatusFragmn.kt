package saintdev.kr.saveref.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.eazegraph.lib.charts.ValueLineChart
import org.eazegraph.lib.models.ValueLinePoint
import org.eazegraph.lib.models.ValueLineSeries
import org.json.JSONArray
import org.json.JSONObject
import saintdev.kr.saveref.R
import saintdev.kr.saveref.models.libs.AppEnv
import saintdev.kr.saveref.models.libs.Parser
import saintdev.kr.saveref.models.libs.openMessageDialog
import saintdev.kr.saveref.models.libs.openProgress
import saintdev.kr.saveref.models.network.HttpManager
import saintdev.kr.saveref.models.network.OnHttpResponse

class StatusFragmn : Fragment() {
    private lateinit var rootView: View
    private lateinit var tempChart: ValueLineChart      // 온도 차트
    private lateinit var humiChart: ValueLineChart      // 습도 차트
    private lateinit var o2Chart: ValueLineChart        // 산소 차트

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragmn_status, container, false)
        this.rootView = v

        // chart 를 찾는다.
        this.tempChart = v.findViewById(R.id.temp_chart)
        this.humiChart = v.findViewById(R.id.humi_chart)
        this.o2Chart = v.findViewById(R.id.o2_chart)

        // chart data 를 업데이트 한다.
        updateData()

        return v
    }

    private fun updateData() {
        val callback = object : OnHttpResponse {
            override fun onSuccess(json: JSONObject) {

                //  실제 데이터 업데이트 파싱 및 가져오기 부분
                val item = Parser.page2Parse(json)

                if(item == null) {
                    "일부 데이터를 파싱 할 수 없습니다. 2".openMessageDialog("오류", this@StatusFragmn.activity)
                } else {
                    updateHumiChart(item.humiChart)
                    updateO2Chart(item.oxygChart)
                    updateTempChart(item.tempChart)
                }
            }

            override fun onFailed(ex: Exception?) {
                if(ex == null) {
                    // 통신 오류
                    "서버가 응답하지 않습니다.".openMessageDialog("오류", this@StatusFragmn.activity)
                } else {
                    // 파싱 오류
                    "${ex.message}\n데이터 파싱에 실패 했습니다.".openMessageDialog("오류", this@StatusFragmn.activity)
                }
            }
        }

        HttpManager(callback, AppEnv.PAGE02_URL).execute()
    }

    /**
     * 온도 차트를 업데이트 한다.
     */
    private fun updateTempChart(arr: ArrayList<JSONObject>) {
        val chartData = ValueLineSeries()
        chartData.color = 0xFF56B7F

        // get chart data.
        arr.forEach {
            chartData.addPoint(ValueLinePoint(it["time"].toString(), (it["temp"] as Double).toFloat()))
        }

        tempChart.addSeries(chartData)
    }

    /**
     * 습도 차트를 업데이트 한다.
     */
    private fun updateHumiChart(arr: ArrayList<JSONObject>) {
        val chartData = ValueLineSeries()
        chartData.color = 0xFF56B7F

        // get chart data.
        arr.forEach {
            chartData.addPoint(ValueLinePoint(it["time"].toString(), it["humi"].toString().toInt().toFloat()))
        }

        humiChart.addSeries(chartData)
    }

    /**
     * 산소 차트를 업데이트 한다.
     */
    private fun updateO2Chart(arr: ArrayList<JSONObject>) {
        val chartData = ValueLineSeries()
        chartData.color = 0xFF56B7F

        // get chart data.
        arr.forEach {
            chartData.addPoint(ValueLinePoint(it["time"].toString(), it["oxyg"].toString().toInt().toFloat()))
        }

        o2Chart.addSeries(chartData)
    }
}