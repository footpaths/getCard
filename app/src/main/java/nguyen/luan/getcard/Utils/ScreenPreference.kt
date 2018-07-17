package nguyen.luan.getcard.Utils

/**
 * Created by PC on 1/17/2018.
 */

import android.content.Context
import android.content.SharedPreferences
import nguyen.luan.getcard.LoginActivity


class ScreenPreference (private val context: Context) {



    private val sharedName = "screenmanager_play"



    private val sharedName1 = "screenmanager_play1"



    private val mPref: SharedPreferences



    private val sharedPreferences: SharedPreferences



    fun getmPref(): SharedPreferences {

        return mPref

    }

    var orderID: String

        get() = sharedPreferences.getString("orderID", "")

        set(value) {

            sharedPreferences.edit().putString("orderID", value).apply()

        }

    var saveFirstApp: Boolean

        get() = sharedPreferences.getBoolean("first", false)

        set(value) {

            sharedPreferences.edit().putBoolean("first", value).apply()

        }



    var saveDeviceID: String

        get() = sharedPreferences.getString("url", "")

        set(value) {

            sharedPreferences.edit().putString("url", value).apply()

        }
    var saveAppName: String

        get() = sharedPreferences.getString("appName", "")

        set(value) {

            sharedPreferences.edit().putString("appName", value).apply()

        }


    var saveName: String

        get() = sharedPreferences.getString("name", "")

        set(value) {

            sharedPreferences.edit().putString("name", value).apply()

        }

    var saveAvatar: String

        get() = sharedPreferences.getString("avatar", "")

        set(value) {

            sharedPreferences.edit().putString("avatar", value).apply()

        }

    var saveEmail: String

        get() = sharedPreferences.getString("email", "")

        set(value) {

            sharedPreferences.edit().putString("email", value).apply()

        }

    var saveTotalPoint: Int

        get() = sharedPreferences.getInt("price", 0)

        set(value) {

            sharedPreferences.edit().putInt("price", value).apply()

        }








    init {

        mPref = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE)

        sharedPreferences = context.getSharedPreferences(sharedName1,

                Context.MODE_PRIVATE)

    }




    companion object {


        lateinit var instance:ScreenPreference


        fun getInstance(context: Context): ScreenPreference {

            instance= ScreenPreference(context)

            return instance


        }


    }





}