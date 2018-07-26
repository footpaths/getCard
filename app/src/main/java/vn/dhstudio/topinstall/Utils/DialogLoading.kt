package vn.dhstudio.topinstall.Utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context

@SuppressLint("StaticFieldLeak")
@Suppress("DEPRECATION")
/**
 * Created by PC on 12/25/2017.
 */
object DialogLoading {
    private var pd: ProgressDialog? = null

    private var progressDialog: ProgressDialog? = null

    internal var context: Context ?= null
    internal var activity: Activity ?=null


    fun ProgressDialogLoader(context: Context) {
        this.context = context
        this.activity = context as Activity
    }

    fun progress_dialog_dismiss() {

        if (pd != null && pd!!.isShowing)
            pd!!.dismiss()

        pd = null


    }

    fun progress_dialog_creation(message:String) {
        try {
            if (pd == null)
                pd = ProgressDialog.show(activity, "", message, true)
        } catch (e: Exception) {

        }

    }

    fun progressdialog_creation(activity: Activity, title: String) {
        try {
            if (progressDialog == null)
                progressDialog = ProgressDialog.show(activity, "", title, true)
        } catch (e: Exception) {

        }

    }

    fun progressdialog_dismiss() {

        if (progressDialog != null && progressDialog!!.isShowing)
            progressDialog!!.dismiss()

        progressDialog = null


    }
}