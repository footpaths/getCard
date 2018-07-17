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
import android.widget.Toast
import nguyen.luan.getcard.MainActivity
import com.google.firebase.database.GenericTypeIndicator
import android.content.ClipData.Item
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import nguyen.luan.getcard.LoginActivity
import nguyen.luan.getcard.adapter.EndlessRecyclerViewScrollListener
import java.util.*


/**
 * Created by PC on 12/8/2017.
 */
class ReceivePointsFragment : Fragment() {



     private val listDevice = ArrayList<DeviceModel>()

    private lateinit var databaseReference: DatabaseReference
    private lateinit var dbChild: DatabaseReference
    private var myAdapter: ListAppReceiveAdapter? = null
    var dataBaseQuery: Query? = null
    var pkgName: String? = null
    var mStringList = ArrayList<String>()
    var listPkgInstall = ArrayList<String>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcvListAppReceive.layoutManager = linearLayoutManager


        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pkgAppsList = activity!!.packageManager.queryIntentActivities(mainIntent, 0)
        instance = this

        //var dialog = DialogLoading
        for (i in 0 until pkgAppsList.size) {
            var info = pkgAppsList[i].activityInfo

            pkgName = info.packageName
            if (pkgName != null) {
                mStringList.add(pkgName!!)
            }


        }




        loadData()


        // println(mStringList)


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



    private fun loadData() {
        listDevice.clear()
        databaseReference = FirebaseDatabase.getInstance().reference
        var androidId = ScreenPreference.instance.saveDeviceID
        dbChild = databaseReference.child("User").child(ScreenPreference.instance.saveEmail).child("Pkg -$androidId")
        dbChild.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (child in p0.children) {
                    listPkgInstall.add(child.value.toString())

                }

            }
        })


        rcvListAppReceive.layoutManager = LinearLayoutManager(activity)
        myAdapter = ListAppReceiveAdapter(this!!.activity!!,listDevice)

        rcvListAppReceive.adapter = myAdapter
//        dbChild = databaseReference.child("listApp")
        dataBaseQuery =  databaseReference.child("listApp").orderByChild("point").limitToLast(20)
        dataBaseQuery!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                listDevice.clear()
                for (child in snapshot.children) {
                     listDevice.add(child.getValue(DeviceModel::class.java)!!)

                }
                listDevice.reverse()

                myAdapter!!.notifyDataSetChanged()
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

    /**
     * lay du lieu qua lai
     */
    companion object {
        lateinit var instance: ReceivePointsFragment
            private set
    }
}