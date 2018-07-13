package nguyen.luan.getcard.adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import nguyen.luan.getcard.R
import nguyen.luan.getcard.model.DeviceModel
import java.util.ArrayList

/**
 * Created by PC on 12/12/2017.
 */
class ListAppAdapter(private val mContext: Context) : RecyclerView.Adapter<ListAppAdapter.MyHolder>() {

    private var listApp = ArrayList<DeviceModel>()

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder?.txtName?.text = listApp[position].name
        Glide.with(mContext).load(listApp[position].icon)
                .placeholder(R.drawable.holderimade)
                .crossFade()
                .thumbnail(0.5f)
                .into(holder?.imgIcon)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_fragment_user, parent, false)
        return MyHolder(v)


    }

/*
    override fun onBindViewHolder(holder: MyHolder?, position: Int) {
//        holder?.txtName?.text = list[position].name
//        holder?.txtContent?.text = list[position].content
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
        return 0
    }

    fun setData(users: List<DeviceModel>) {
        listApp.clear()
        listApp.addAll(users)
        notifyDataSetChanged()

    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(p0: View?) {
            ListAppAdapter.clickListener?.OnItemClick(adapterPosition, p0!!)
        }

        var imgIcon: ImageView = itemView.findViewById(R.id.imgIcon)
        var txtName: TextView = itemView.findViewById(R.id.txtName)

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
    }

    companion object {
        var clickListener: ClickListener? = null
    }
}