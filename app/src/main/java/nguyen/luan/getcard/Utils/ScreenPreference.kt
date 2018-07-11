package nguyen.luan.getcard.Utils

/**
 * Created by PC on 1/17/2018.
 */

import android.content.Context
import android.content.SharedPreferences



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



    var saveFirstName: String

        get() = sharedPreferences.getString("url", "")

        set(value) {

            sharedPreferences.edit().putString("url", value).apply()

        }



    var saveLastname: String

        get() = sharedPreferences.getString("shop_id", "")

        set(value) {

            sharedPreferences.edit().putString("shop_id", value).apply()

        }

    var savePhone: String

        get() = sharedPreferences.getString("phone", "")

        set(value) {

            sharedPreferences.edit().putString("phone", value).apply()

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



/*

    fun setCartList(tour: CartModel) {

        var list: ArrayList<CartModel>?= ArrayList()



        list?.addAll(getCartList())



        list?.add(tour)

        val gson = Gson()

        val json = gson.toJson(list)

        sharedPreferences.edit().putString("cartList", json).apply()

    }





    fun getCartList(): ArrayList<CartModel> {

        val gson = Gson()

        var listtemp: ArrayList<CartModel>?= ArrayList()

        val jsontemp = gson.toJson(listtemp)

        val json = sharedPreferences.getString("cartList", jsontemp)

        val type = object : TypeToken<ArrayList<CartModel>>() {



        }.type

        return gson.fromJson(json, type)

    }



    fun removeCartList(pos:Int){

        var list: ArrayList<CartModel>?= ArrayList()



        list?.addAll(getCartList())



        list?.removeAt(pos)

        val gson = Gson()

        val json = gson.toJson(list)

        sharedPreferences.edit().putString("cartList", json).apply()

    }

    fun deleteTourList(){

        var list: ArrayList<CartModel>?= ArrayList()

        val gson = Gson()

        val json = gson.toJson(list)

        sharedPreferences.edit().putString("cartList", json).apply()

    }



    fun setCartListLayout(tour: CartModelLayout) {

        var list: ArrayList<CartModelLayout>?= ArrayList()



        list?.addAll(getCartListLayout())



        list?.add(tour)

        val gson = Gson()

        val json = gson.toJson(list)

        sharedPreferences.edit().putString("cartListLayout", json).apply()

    }

    fun getCartListLayout(): ArrayList<CartModelLayout> {

        val gson = Gson()

        var listtemp: ArrayList<CartModelLayout>?= ArrayList()

        val jsontemp = gson.toJson(listtemp)

        val json = sharedPreferences.getString("cartListLayout", jsontemp)

        val type = object : TypeToken<ArrayList<CartModelLayout>>() {



        }.type

        return gson.fromJson(json, type)

    }



    fun removeCartListLayout(pos:Int){

        var list: ArrayList<CartModelLayout>?= ArrayList()



        list?.addAll(getCartListLayout())



        list?.removeAt(pos)

        val gson = Gson()

        val json = gson.toJson(list)

        sharedPreferences.edit().putString("cartListLayout", json).apply()

    }

    fun deleteCartListLayout(){

        var list: ArrayList<CartModelLayout>?= ArrayList()

        val gson = Gson()

        val json = gson.toJson(list)

        sharedPreferences.edit().putString("cartListLayout", json).apply()

    }*/



    var ischecklogin: Boolean

        get() = sharedPreferences.getBoolean("login", false)

        set(value) {

            sharedPreferences.edit().putBoolean("login", value).apply()

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