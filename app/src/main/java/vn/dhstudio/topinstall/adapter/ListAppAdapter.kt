package vn.dhstudio.topinstall.adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.google.firebase.database.*
import vn.dhstudio.topinstall.R
import vn.dhstudio.topinstall.Utils.ScreenPreference
import vn.dhstudio.topinstall.fragment.ChangeCardFragment
import vn.dhstudio.topinstall.model.DeviceModel
import java.util.*

/**
 * Created by PC on 12/12/2017.
 */
class ListAppAdapter(private val mContext: Context, var listApp: ArrayList<DeviceModel>) : RecyclerView.Adapter<ListAppAdapter.MyHolder>() {

    //    private var listApp = ArrayList<DeviceModel>()
    internal var databaseReference = FirebaseDatabase.getInstance().reference
    var afterPoint = 0
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.txtName?.text = listApp[position].name
        holder.txtPoint?.text = "P: " + listApp[position].point!!.toInt()
        Glide.with(mContext).load(listApp[position].icon)
                .placeholder(R.drawable.holderimade)
                .crossFade()
                .into(holder?.imgIcon)
        holder.btnEdit.setOnClickListener {
            databaseReference.child("User").child(ScreenPreference.instance.saveEmail).child(ScreenPreference.instance.saveDeviceID).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val d = Dialog(mContext)
                    d.setContentView(R.layout.input_dialog_point)
                    var edPoint: EditText


                    edPoint = d.findViewById<View>(R.id.edPoint) as EditText
//                    edPoint.hint = listApp[position].name.toString()
                    edPoint.setHint(listApp[position].name.toString())


                    edPoint.setText(listApp[position].point!!.toInt().toString())
                    var totalPoint = ChangeCardFragment.instance.point + edPoint.text.toString().toInt()


                    var btnSave = d.findViewById<View>(R.id.btnSave) as Button
                    btnSave.setOnClickListener {
                        val userTemps = dataSnapshot.getValue(DeviceModel::class.java)

                        if (edPoint.text.toString().toInt() in 1..totalPoint) {
                            var point = edPoint.text.toString().toInt()
                            var fpoint = String.format("%06d", point)
                            userTemps!!.point = fpoint
                            userTemps!!.icon = listApp[position].icon
                            userTemps!!.name = listApp[position].name
                            userTemps!!.email = listApp[position].email
                            userTemps!!.idDevice = listApp[position].idDevice
                            userTemps!!.packageParams = listApp[position].packageParams
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
                                                    Toast.makeText(mContext, "update error!!", Toast.LENGTH_LONG).show()


                                                } else {
                                                    Toast.makeText(mContext, "update success!! ", Toast.LENGTH_LONG).show()
                                                    clickListener?.OnItemClickUpdate()
                                                    afterPoint = totalPoint - point
                                                    val database = FirebaseDatabase.getInstance()
                                                    val userInfo = database.getReference("User")
                                                    userInfo.child(ScreenPreference.instance.saveEmail).child("userPoint").setValue(afterPoint.toString())
                                                }
                                            }

                                    databaseReference.child("listApp")

                                            .child(snapshot.key.toString() + "-" + ScreenPreference.instance.saveDeviceID)
                                            .setValue(userTemps) { databaseError, databaseReference ->
                                                if (databaseError != null) {
                                                    Toast.makeText(mContext, "update error!!", Toast.LENGTH_LONG).show()


                                                } else {
                                                    Toast.makeText(mContext, "update success!!  ", Toast.LENGTH_LONG).show()
//                                                clickListener?.OnItemClickUpdate()
                                                }
                                            }
                                    d.dismiss()

                                }
                            }
                        } else {
                            d.dismiss()
                            Toast.makeText(mContext, "point must be in 0 to $totalPoint ", Toast.LENGTH_LONG).show()
                        }
                    }


                    d.show()


                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_fragment_user, parent, false)
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

    fun setData(users: List<DeviceModel>) {
        listApp.clear()
        listApp.addAll(users)
        listApp.reverse()
        notifyDataSetChanged()

    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(p0: View?) {
            ListAppAdapter.clickListener?.OnItemClick(adapterPosition, p0!!)
        }

        var imgIcon: ImageView = itemView.findViewById(R.id.imgIcon)
        var txtName: TextView = itemView.findViewById(R.id.txtName)
        var txtPoint: TextView = itemView.findViewById(R.id.txtPoint)
        var btnEdit: Button = itemView.findViewById(R.id.btnEdit)

        //        var txtContent : TextView = itemView.findViewById(R.id.txtContent)
//        var txtDate : TextView = itemView.findViewById(R.id.txtDate)
//        var thumbImage: ImageView = itemView.findViewById(R.id.thumbImage)
//        var tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        init {
            itemView.setOnClickListener(this)
        }
    }

    fun setOnItemClickListener(clickListener: ListAppAdapter.ClickListener) {
        ListAppAdapter.clickListener = clickListener
    }

    interface ClickListener {
        fun OnItemClick(position: Int, v: View)
        fun OnItemClickUpdate()

    }

    companion object {
        var clickListener: ClickListener? = null
    }
}