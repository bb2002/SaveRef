package saintdev.kr.saveref.models.network

import android.os.AsyncTask
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class HttpManager(val listener: OnHttpResponse, val url: String) {
    fun execute() = HttpRequestTask(listener).execute(url)

    class HttpRequestTask(listener: OnHttpResponse) : AsyncTask<String, Void, String>() {
        private val client: OkHttpClient = OkHttpClient()
        private val JSON = MediaType.parse("application/json; charset=utf-8")
        private val callback: OnHttpResponse = listener

        override fun doInBackground(vararg url: String?): String {
            val link = url[0] ?: ""

            try {
                return if (link.isNotEmpty()) {
                    val reqBody = RequestBody.create(JSON, "")






























































































































































































































































































































































































































































































                    val request = Request.Builder()
                            .url(link)
                            .post(reqBody)
                            .build()

                    val response = client.newCall(request).execute()
                    //response.body()?.string() ?: ""
                    val asdf = response.body()!!.string()

                    asdf
                } else {
                    ""
                }
            } catch(ex: Exception) {
                return ""
            }

        }

        /**
         * 응답을 받았습니다.
         */
        override fun onPostExecute(json: String) {
            super.onPostExecute(json)

            if(json.isEmpty()) {
                // 응답 오류, 데이터 없슴
                callback.onFailed(null)
            } else {
                // 응답 성공, 데이터 있슴
                try {
                    val convJson = JSONObject(json)
                    callback.onSuccess(convJson)
                } catch(ex: Exception) {
                    callback.onFailed(ex)
                }
            }
        }
    }
}

/**
 * Http response listener
 */
interface OnHttpResponse {
    fun onSuccess(json: JSONObject)
    fun onFailed(ex: Exception?)
}