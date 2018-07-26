package vn.dhstudio.topinstall.Utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast

/**
 * Created by PC on 12/25/2017.
 */
object CheckNetwork {
    //			********************************************************************************************
    //	 										IF NETWORK IS ACTIVE OR NOT
    //			********************************************************************************************
    fun isNetworkAvailable(context: Context): Boolean {
        var networkAvailable = false


        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo as NetworkInfo

            if (connectivityManager != null && activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting) {
                networkAvailable = true
            } else {
                networkAvailable = false
                Toast.makeText(context as Activity, "Connection Failed", Toast.LENGTH_LONG).show()

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return networkAvailable
    }


}