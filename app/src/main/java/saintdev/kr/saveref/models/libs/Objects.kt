package saintdev.kr.saveref.models.libs

import android.graphics.Bitmap
import org.json.JSONObject


/**
 * Food 에 대한 데이터 오브젝트
 */
data class FoodItem(
        val foodIcon: Bitmap,
        val foodName: String,
        val foodId: Int
)

/**
 * 페이지 1 데이터 클레스
 */
data class Page1Item(
        val doorOpenCount: Int,
        val doorOpenTime: Int,
        val usedPower: Double,
        val usedPowerRate: Int,
        val exportedCo2: Int
)

/**
 * 페이지 2 데이터 클레스
 */
data class Page2Item(
        val tempChart: ArrayList<JSONObject>,
        val humiChart: ArrayList<JSONObject>,
        val oxygChart: ArrayList<JSONObject>
)