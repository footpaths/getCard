package nguyen.luan.getcard.fragment

import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.receive_points_fragment.*
import nguyen.luan.getcard.R
import nguyen.luan.getcard.Utils.ScreenPreference
import nguyen.luan.getcard.adapter.ListAppAdapter
import nguyen.luan.getcard.adapter.ListAppReceiveAdapter
import nguyen.luan.getcard.model.DeviceModel
import nguyen.luan.getcard.model.Emails
import java.util.ArrayList
import android.widget.Toast
import nguyen.luan.getcard.MainActivity
import com.google.firebase.database.GenericTypeIndicator
import android.content.ClipData.Item
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import nguyen.luan.getcard.adapter.EndlessRecyclerViewScrollListener


/**
 * Created by PC on 12/8/2017.
 */
class ReceivePointsFragment : Fragment() {


    private val listApp = ArrayList<Emails>()
    private val listDevice = ArrayList<DeviceModel>()

    private lateinit var databaseReference: DatabaseReference
    private lateinit var dbChild: DatabaseReference
    private var myAdapter: ListAppReceiveAdapter? = null
    var dataBaseQuery:Query ?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcvListAppReceive.layoutManager = linearLayoutManager

         loadData()
        rcvListAppReceive?.addOnScrollListener(object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            /*  override fun onLoadMore(page: Int, totalItemsCount: Int) {
                //callService(totalItemsCount.toString())
            }*/


            override fun onLoadMore(page: Int, totalItemsCount: Int) {

             }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.receive_points_fragment, container, false)

        return view
    }

    private fun displayUsers(ls: ArrayList<DeviceModel>) {

        myAdapter!!.setData(ls)

    }

    private fun loadData() {
        listApp.clear()
        databaseReference = FirebaseDatabase.getInstance().reference
        rcvListAppReceive.layoutManager = LinearLayoutManager(activity)
        myAdapter = ListAppReceiveAdapter(this!!.activity!!)

        rcvListAppReceive.adapter = myAdapter
        dbChild = databaseReference.child("listApp")
        dataBaseQuery = dbChild.orderByChild("point").limitToLast(20)
        dataBaseQuery!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {

                        listDevice.add(child.getValue(DeviceModel::class.java)!!)

                        println(listDevice)


//                    var device: DeviceModel = child.getValue(DeviceModel::class.java)!!
//                    listDevice.add(device)
//                     listDevice.add(child.getValue(DeviceModel::class.java)!!)
//                    println(listDevice)
//                    displayUsers(listDevice)
                }
                listDevice.reverse()
                displayUsers(listDevice)
            }
        })

        myAdapter!!.setOnItemClickListener(object : ListAppReceiveAdapter.ClickListener {
            override fun OnItemClick(position: Int, v: View) {

            }

            override fun OnItemClickUpdate() {
                loadData()
            }
        })
    }


}