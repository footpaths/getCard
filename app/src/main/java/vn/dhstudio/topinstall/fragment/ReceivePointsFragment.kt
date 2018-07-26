package vn.dhstudio.topinstall.fragment

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import vn.dhstudio.topinstall.R
import vn.dhstudio.topinstall.Utils.ScreenPreference
import vn.dhstudio.topinstall.adapter.ListAppReceiveAdapter
import vn.dhstudio.topinstall.model.DeviceModel
import android.content.Intent
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import vn.dhstudio.topinstall.adapter.EndlessRecyclerViewScrollListener
import java.util.*


/**
 * Created by PC on 12/8/2017.
 */
class ReceivePointsFragment : Fragment() {


    private val listDevice = ArrayList<DeviceModel>()
    private val listDeviceTemp = ArrayList<DeviceModel>()
    private var currentVisibleItemCount: Int = 0
    private var currentScrollState: Int = 0
    private var currentFirstVisibleItem: Int = 0
    private var totalItem: Int = 0
    private var lBelow: LinearLayout? = null

    var mAdView:AdView?=null

    private lateinit var databaseReference: DatabaseReference
    private lateinit var dbChild: DatabaseReference
    private lateinit var dbAddPoint: DatabaseReference
    private lateinit var dbListUserInstallApp: DatabaseReference
    private var myAdapter: ListAppReceiveAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var rcvListAppReceive: RecyclerView? = null
    var statusInstall = false
    var appPackageName: String? = null
    var dataBaseQuery: Query? = null
    var pkgName: String? = null
    var listAppinDevice = ArrayList<String>()
    var listPkgInstall = ArrayList<String>()
    var listUserInstallApp = ArrayList<String>()
    var userInfo: DatabaseReference? = null
    var orderOldId = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcvListAppReceive?.layoutManager = linearLayoutManager
        instance = this
        val database = FirebaseDatabase.getInstance()
        userInfo = database.getReference("User")


    }

    override fun onResume() {
        super.onResume()
        loadListAppinDevice()
        checkInstall()

//        loadData()

    }

    private fun checkInstall() {
        println(statusInstall)
        loadData()

        if (statusInstall) {
            if (listAppinDevice != null) {
                if (listAppinDevice.contains(appPackageName)) {

                    userInfo!!.child(ScreenPreference.instance.saveEmail).child("listUserInstall -" + ScreenPreference.instance.saveDeviceID).push().setValue(appPackageName)

                    databaseReference.child("User").child(ScreenPreference.instance.saveEmailOther)
                            .child(ScreenPreference.instance.saveAndroidIdOther)
                            .child(ScreenPreference.instance.saveNameOther).addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {


                                    val userTemps = dataSnapshot.getValue(DeviceModel::class.java)


                                    var point = userTemps!!.point!!.toInt() - 1
                                    if(point<0){
                                        point = 0
                                    }
                                    var fpoint = String.format("%06d", point)
                                    userTemps!!.point = fpoint




                                    databaseReference.child("User")
                                            .child(ScreenPreference.instance.saveEmailOther)
                                            .child(ScreenPreference.instance.saveAndroidIdOther)
                                            .child(ScreenPreference.instance.saveNameOther)
                                            .setValue(userTemps) { databaseError, databaseReference ->
                                                if (databaseError != null) {
                                                    Toast.makeText(activity, "update error!!", Toast.LENGTH_LONG).show()


                                                } else {
                                                    Toast.makeText(activity, "update success!!", Toast.LENGTH_LONG).show()
                                                    val userInfo = FirebaseDatabase.getInstance().getReference("User")
                                                    userInfo.child(ScreenPreference.instance.saveEmail).child("userPoint").addListenerForSingleValueEvent(object : ValueEventListener {
                                                        override fun onCancelled(p0: DatabaseError) {

                                                        }

                                                        override fun onDataChange(p0: DataSnapshot) {
                                                            var totalPoint = p0.value.toString().toInt() + 1
                                                            userInfo.child(ScreenPreference.instance.saveEmail).child("userPoint").setValue(totalPoint.toString())
                                                        }
                                                    })

                                                }
                                            }

                                    databaseReference.child("listApp")

                                            .child(ScreenPreference.instance.saveNameOther + "-" + ScreenPreference.instance.saveDeviceID)
                                            .setValue(userTemps) { databaseError, databaseReference ->
                                                if (databaseError != null) {
                                                    Toast.makeText(activity, "update error!!", Toast.LENGTH_LONG).show()


                                                } else {
                                                    Toast.makeText(activity, "update success!! ", Toast.LENGTH_LONG).show()
//                                                clickListener?.OnItemClickUpdate()
                                                }
                                            }


                                }

                                override fun onCancelled(databaseError: DatabaseError) {

                                }
                            })


                }
            }

        } else {

        }
    }

    private fun loadListAppinDevice() {

        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pkgAppsList = activity!!.packageManager.queryIntentActivities(mainIntent, 0)
        listAppinDevice.clear()


        //var dialog = DialogLoading
        for (i in 0 until pkgAppsList.size) {
            var info = pkgAppsList[i].activityInfo
            pkgName = info.packageName
            if (pkgName != null) {
                listAppinDevice.add(pkgName!!)
            }


        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.receive_points_fragment, container, false)
        rcvListAppReceive = view.findViewById(R.id.rcvListAppReceive)
      /*  MobileAds.initialize(activity,
                "ca-app-pub-8443310519906912~1488393992")*/
        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().addTestDevice("74d714a239d5f7ea").build()
        mAdView?.loadAd(adRequest)
        return view
    }


    private fun loadData() {
        listDevice.clear()
        databaseReference = FirebaseDatabase.getInstance().reference
        var androidId = ScreenPreference.getInstance(activity!!).saveDeviceID
        dbChild = databaseReference.child("User").child(ScreenPreference.instance.saveEmail).child("Pkg -$androidId")
        dbListUserInstallApp = databaseReference.child("User").child(ScreenPreference.instance.saveEmail).child("listUserInstall -" + ScreenPreference.instance.saveDeviceID)
        dbChild.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (child in p0.children) {

                    listPkgInstall.add(child.value.toString())

                }

            }
        })

        dbListUserInstallApp.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                listUserInstallApp.clear()
                for (child in p0.children) {
                    listUserInstallApp.add(child.value.toString())

                }
                println(listUserInstallApp)
            }
        })


//        rcvListAppReceive?.layoutManager = LinearLayoutManager(activity)
        myAdapter = ListAppReceiveAdapter(this!!.activity!!, listDevice)

        rcvListAppReceive?.adapter = myAdapter
//        dbChild = databaseReference.child("listApp")
        dataBaseQuery = databaseReference.child("listApp").orderByChild("point").limitToLast(30)
        dataBaseQuery!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                listDevice.clear()
                for (child in snapshot.children) {
                    orderOldId = child.key!!


                    listDeviceTemp.clear()
                    listDeviceTemp.add(child.getValue(DeviceModel::class.java)!!)
                    if (!listUserInstallApp.contains(listDeviceTemp[0].packageParams.toString())) {
                        if (!listAppinDevice.contains(listDeviceTemp[0].packageParams.toString())) {
                            listDevice.add(child.getValue(DeviceModel::class.java)!!)
                        }
                    }

                }

                listDevice.reverse()

                myAdapter!!.notifyDataSetChanged()
            }
        })


        rcvListAppReceive?.addOnScrollListener(object : EndlessRecyclerViewScrollListener(linearLayoutManager!!) {

            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                dataBaseQuery = databaseReference.child("listApp").orderByChild("point").limitToFirst(totalItemsCount + 30)
                dataBaseQuery!!.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        listDevice.clear()
                        for (child in snapshot.children) {
                            orderOldId = child.key!!


                            listDeviceTemp.clear()
                            listDeviceTemp.add(child.getValue(DeviceModel::class.java)!!)
                            if (!listUserInstallApp.contains(listDeviceTemp[0].packageParams.toString())) {
                                if (!listAppinDevice.contains(listDeviceTemp[0].packageParams.toString())) {
                                    listDevice.add(child.getValue(DeviceModel::class.java)!!)
                                }
                            }

                        }

                        listDevice.reverse()
                        myAdapter!!.notifyDataSetChanged()
                    }
                })
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