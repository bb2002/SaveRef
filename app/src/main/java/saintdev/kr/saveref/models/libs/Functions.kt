package saintdev.kr.saveref.models.libs

import android.app.Activity
import android.app.ProgressDialog
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import org.json.JSONArray
import org.json.JSONObject
import saintdev.kr.saveref.R

/**
 * App const
 */
object AppEnv {
    val PAGE01_URL = "http://3pmakers.xyz/api1"
    val PAGE02_URL = "http://3pmakers.xyz/api2"
    val PAGE03_URL = "http://3pmakers.xyz/api3"
}

/**
 * Extends function.
 */
fun Int.bitmap(res: Resources) = BitmapFactory.decodeResource(res, this)

fun String.openMessageDialog(title: String, activity: Activity) : AlertDialog {
    return AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(this)
            .setPositiveButton("예") { dialogInterface, _ -> dialogInterface.dismiss() }
            .show()
}

fun String.openProgress(activity: Activity) : ProgressDialog {
    val dialog = ProgressDialog(activity)
    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    dialog.setMessage(this)
    dialog.show()

    return dialog
}

object Parser {
    /**
     * 페이지 1 에서 받은 데이터를 파싱 한다.
     */
    fun page1Parse(obj: JSONObject) : Page1Item? {

        return try {
            Page1Item(
                    obj["doorOpenCount"].toString().toInt(),
                    obj["doorOpenTime"].toString().toInt(),
                    obj["usedPower"].toString().toDouble(),
                    obj["usedPowerRate"].toString().toInt(),
                    obj["exportedCo2"].toString().toInt())
        } catch(ex: Exception) {
            null
        }
    }

    /**
     * 페이지 2 에서 받은 데이터를 파싱한다.
     */
    fun page2Parse(obj: JSONObject) : Page2Item? {

        // 온도 부분 구하기
        return try {
            val tempArray = arrayListOf<JSONObject>()
            val tempChart = JSONArray(obj["temperatureChart"].toString())
            for (i in 0 until tempChart.length()) tempArray.add(tempChart[i] as JSONObject)

            // 습도 부분 구하기
            val humiArray = arrayListOf<JSONObject>()
            val humiChart = JSONArray(obj["humidityChart"].toString())
            for (i in 0 until humiChart.length()) humiArray.add(humiChart[i] as JSONObject)

            // 산소 부분 구하기
            val oxygArray = arrayListOf<JSONObject>()
            val oxygChart = JSONArray(obj["oxygenChart"].toString())
            for (i in 0 until oxygChart.length()) oxygArray.add(oxygChart[i] as JSONObject)

            Page2Item(tempArray, humiArray, oxygArray)
        } catch(ex: Exception) {
            null
        }
    }
}

/**
 * id 값을 FoodItem 으로 전환 한다.
 */
fun idToFoodItem(id: Int, res: Resources) : FoodItem {
    return when(id) {
        18 -> FoodItem(R.drawable.ic_apple.bitmap(res), "사과", 18)
        117 -> FoodItem(R.drawable.ic_egg.bitmap(res), "달걀", 117)
        35 -> FoodItem(R.drawable.ic_fish.bitmap(res), "생선", 35)
        else -> FoodItem(R.drawable.ic_apple.bitmap(res), "사과", 18)
    }
}