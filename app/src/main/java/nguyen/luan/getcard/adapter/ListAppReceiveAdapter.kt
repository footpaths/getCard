package nguyen.luan.getcard.adapter

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.google.firebase.database.*
import nguyen.luan.getcard.R
import nguyen.luan.getcard.Utils.DialogLoading
import nguyen.luan.getcard.model.DeviceModel
import kotlin.collections.ArrayList
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import android.content.pm.ResolveInfo
import android.graphics.Color
import nguyen.luan.getcard.fragment.ReceivePointsFragment


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
        var pkg = listApp[position].packageParams
        println(pkg)
        for(i in 0 until listPkgInstall.size){
            println(listPkgInstall[i])
            if (pkg == listPkgInstall[i]){
                holder.btnInstall.isEnabled= false
                holder.btnInstall.setBackgroundColor(Color.RED)
               // holder.btnInstall.setTextColor(Color.WHITE)
            }else{
               // holder.btnInstall.resources.getColor(R.color.blue)
            }
        }

        //   dialog.ProgressDialogLoader(mContext)

        holder.btnInstall.setOnClickListener {
            // dialog.progress_dialog_creation("Checking...")
            //
            val isAppInstalled = appInstalledOrNot(listApp[position].packageParams.toString())
            var appPackageName = listApp[position].packageParams.toString()
//
            if (isAppInstalled) {
//            //This intent will help you to launch if the package is already installed
                /// val LaunchIntent = mContext.packageManager.getLaunchIntentForPackage("biz.gina.southernbreezetour")
                // mContext.startActivity(LaunchIntent)
                //    dialog.progress_dialog_dismiss()
                Toast.makeText(mContext, "Application is already installed.", Toast.LENGTH_SHORT).show()
                //   Log.i("kq", "Application is already installed.")
            } else {
                try {
                    mContext.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                } catch (anfe: android.content.ActivityNotFoundException) {
                    mContext.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
                }

                // Do whatever we want to do if application not installed
                // For example, Redirect to play store
                //   dialog.progress_dialog_dismiss()
                //   Toast.makeText(mContext, "Application is already installed.", Toast.LENGTH_SHORT).show()

                //  Log.i("kq", "Application is not currently installed.")

            }
        }

        /*    holder.btnEdit.setOnClickListener {
                databaseReference.child("User").child(ScreenPreference.instance.saveEmail).child(ScreenPreference.instance.saveDeviceID).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val d = Dialog(mContext)
                        d.setContentView(R.layout.cell_fragment_user_install)
                        var edPoint: EditText


                        edPoint = d.findViewById<View>(R.id.edPoint) as EditText


                        edPoint.setText(listApp[position].point)



                        var btnSave = d.findViewById<View>(R.id.btnSave) as Button
                        btnSave.setOnClickListener {
                            val userTemps = dataSnapshot.getValue(DeviceModel::class.java)
                            var point = edPoint.text.toString().toInt().toString()
                            userTemps!!.point = point
                            userTemps!!.icon = listApp[position].icon
                            userTemps!!.name = listApp[position].name
                            userTemps!!.status = listApp[position].status
                            userTemps!!.idParams = listApp[position].idParams
                            for (snapshot in dataSnapshot.children) {
                                val userTemp = snapshot.getValue(DeviceModel::class.java)
                                if (listApp[position].idParams == userTemp?.idParams) {

                                    databaseReference.child("User")
                                            .child(ScreenPreference.instance.saveEmail)
                                            .child(ScreenPreference.instance.saveDeviceID)
                                            .child(snapshot.key.toString())
                                            .setValue(userTemps) { databaseError, databaseReference ->
                                                if (databaseError != null) {
                                                    Toast.makeText(mContext, "Cập nhật Error!!", Toast.LENGTH_LONG).show()


                                                } else {
                                                    Toast.makeText(mContext, "Cập nhật thành công!! ", Toast.LENGTH_LONG).show()
                                                    clickListener?.OnItemClickUpdate()
                                                }
                                            }

                                    databaseReference.child("listApp")
                                            .child(ScreenPreference.instance.saveEmail)
                                             .child(snapshot.key.toString())
                                            .setValue(userTemps) { databaseError, databaseReference ->
                                                if (databaseError != null) {
                                                    Toast.makeText(mContext, "Cập nhật Error!!", Toast.LENGTH_LONG).show()


                                                } else {
                                                    Toast.makeText(mContext, "Cập nhật thành công!! ", Toast.LENGTH_LONG).show()
                                                    clickListener?.OnItemClickUpdate()
                                                }
                                            }
                                    d.dismiss()

                                }
                            }
                        }


                        d.show()


                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })
            }*/

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

/*
    override fun onBindViewHolder(holder: MyHolder?, position: Int) {
        holder?.txtName?.text = list[position].name
        holder?.txtContent?.text = list[position].content
//        holder?.txtDate?.text = list[position].date
//        holder?.tvPrice?.text ="$ "+list[position].hotelPriceSummaries[0].totalPrice.toString()
//        var url=""
//        if(list[position].hotelFiles.size!=0){
//            url=list[position].hotelFiles[0].fileUrl
//        }
//        Glide.with(context).load(url)
//                .placeholder(R.drawable.holderimade)
//                .crossFade()
//                .thumbnail(0.5f)
//                .into(holder?.thumbImage)

    }*/

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