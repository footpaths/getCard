package vn.dhstudio.topinstall.adapter

import android.content.Context
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import vn.dhstudio.topinstall.R
import vn.dhstudio.topinstall.model.DeviceModel
import kotlin.collections.ArrayList
import android.content.Intent
import android.net.Uri
import vn.dhstudio.topinstall.Utils.ScreenPreference
import vn.dhstudio.topinstall.fragment.ReceivePointsFragment


/**
 * Created by PC on 12/12/2017.
 */
class ListAppReceiveAdapter(private val mContext: Context,var listApp : ArrayList<DeviceModel>) : RecyclerView.Adapter<ListAppReceiveAdapter.MyHolder>() {

     var listPkgInstall = java.util.ArrayList<String>()

     override fun onBindViewHolder(holder: MyHolder, position: Int) {
        listPkgInstall =  ReceivePointsFragment.instance.listPkgInstall

        holder.txtName?.text = listApp[position].name
        holder.txtPoint?.text = "P: " + listApp[position].point?.toInt()
        Glide.with(mContext).load(listApp[position].icon)
                .placeholder(R.drawable.holderimade)
                .crossFade()
                .into(holder?.imgIcon)


        holder.btnInstall.setOnClickListener {
            // dialog.progress_dialog_creation("Checking...")
            //
            val isAppInstalled = appInstalledOrNot(listApp[position].packageParams.toString())
               ReceivePointsFragment.instance.appPackageName = listApp[position].packageParams.toString()

            if (isAppInstalled) {

                Toast.makeText(mContext, "Application is already installed.", Toast.LENGTH_SHORT).show()
                //   Log.i("kq", "Application is already installed.")
            } else {
                try {
                    mContext.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+ReceivePointsFragment.instance.appPackageName)))
                    ReceivePointsFragment.instance.statusInstall = true
                    ScreenPreference.instance.saveEmailOther = listApp[position].email!!
                    ScreenPreference.instance.saveAndroidIdOther = listApp[position].idDevice!!
                    ScreenPreference.instance.saveNameOther = listApp[position].name!!
                } catch (anfe: android.content.ActivityNotFoundException) {
                    ReceivePointsFragment.instance.statusInstall = true
                    ScreenPreference.instance.saveEmailOther = listApp[position].email!!
                    ScreenPreference.instance.saveAndroidIdOther = listApp[position].idDevice!!
                    ScreenPreference.instance.saveNameOther = listApp[position].name!!
                    mContext.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+ReceivePointsFragment.instance.appPackageName)))
                }
            }
        }


    }

    //
    private fun appInstalledOrNot(uri: String): Boolean {
        val pm = mContext.packageManager
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }

        return false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_fragment_user_install, parent, false)
        return MyHolder(v)


    }



    override fun getItemCount(): Int {
        return listApp.size
    }



    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(p0: View?) {
            ListAppAdapter.clickListener?.OnItemClick(adapterPosition, p0!!)
        }

        var imgIcon: ImageView = itemView.findViewById(R.id.imgIcon)
        var txtName: TextView = itemView.findViewById(R.id.txtName)
        var txtPoint: TextView = itemView.findViewById(R.id.txtPoint)
        var btnInstall: Button = itemView.findViewById(R.id.btnInstall)

        //        var txtContent : TextView = itemView.findViewById(R.id.txtContent)
//        var txtDate : TextView = itemView.findViewById(R.id.txtDate)
//        var thumbImage: ImageView = itemView.findViewById(R.id.thumbImage)
//        var tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        init {
            itemView.setOnClickListener(this)
        }
    }

    fun setOnItemClickListener(clickListener: ListAppReceiveAdapter.ClickListener) {
        ListAppReceiveAdapter.clickListener = clickListener
    }

    interface ClickListener {
        fun OnItemClick(position: Int, v: View)
        fun OnItemClickUpdate()

    }

    companion object {
        var clickListener: ClickListener? = null
    }
}