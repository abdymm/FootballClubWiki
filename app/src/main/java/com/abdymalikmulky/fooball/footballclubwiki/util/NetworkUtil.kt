package id.or.ypt.agendaapp.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by abdymalikmulky on 11/3/17.
 */
class NetworkUtil {
    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            var isAvailable = false

            val ConnectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = ConnectionManager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected == true) {
                isAvailable = true
            }
            return isAvailable
        }
    }

}