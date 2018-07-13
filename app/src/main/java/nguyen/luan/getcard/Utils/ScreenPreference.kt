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

    var saveCounter: Int

        get() = sharedPreferences.getInt("number_retry", 0)

        set(value) {

            sharedPreferences.edit().putInt("number_retry", value).apply()

        }



    var saveDeviceID: String

        get() = sharedPreferences.getString("url", "")

        set(value) {

            sharedPreferences.edit().putString("url", value).apply()

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

    var saveTotalPrice: String

        get() = sharedPreferences.getString("price", "")

        set(value) {

            sharedPreferences.edit().putString("price", value).apply()

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