package com.example.mytrip

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.util.Log
import java.net.HttpURLConnection
import java.net.URL


@Suppress("DEPRECATION")
data class TesteConnectionInternet(val context: Context) {

    companion object {
        var isConnect: Boolean? = false
    }

    fun isConnected(): Boolean? {
        val cont = context
        val connManager = cont.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connManager.activeNetworkInfo

        if (connManager != null) {
            if (activeNetwork?.isConnectedOrConnecting == true) {
                Log.w("isconnected", "Connecting True")
                //Verifica internet pela WIFI
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                    Log.w("isconnected", "Wifi true")
                    isConnect = true
                }
                //Verifica se tem internet móvel
                if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                    Log.w("isconnected", "Mobile true")
                    isConnect = true
                }
            }
        }
        return isConnect
    }

    class GetUrlDisponivel : AsyncTask<Void, Void, Boolean>() {

        private var iThreadResult: IThreadResult<Boolean>? = null

        override fun doInBackground(vararg params: Void?): Boolean? {
            var isInternet: Boolean? = false
            try {
                Log.w("isconnected", "doInBackground...")

                val urlName = "https://www.google.com/"

                val urlConnection: HttpURLConnection?
                val url = URL(urlName)
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 10000
                urlConnection.readTimeout = 10000
                urlConnection.connect()
                if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                    isInternet = true
                }

            } catch (e: Exception) {
                Log.w("isconnected", "Exception: ${e.message}")
                e.printStackTrace()
                isInternet = false
            }
           return isInternet
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            isConnect = result
            if (iThreadResult != null)
                result?.let { iThreadResult!!.onResult(it) }
        }

        fun setOnResult(iThreadResult: IThreadResult<Boolean>) {
            this.iThreadResult = iThreadResult
        }
    }
}