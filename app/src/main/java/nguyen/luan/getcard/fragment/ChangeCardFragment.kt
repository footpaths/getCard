package nguyen.luan.getcard.fragment

 import android.app.Dialog
 import android.os.AsyncTask
 import android.app.ProgressDialog
 import android.graphics.PorterDuff
 import android.os.Bundle
 import android.provider.Settings
 import android.util.Log

 import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
 import android.widget.Button
 import android.widget.EditText
 import android.widget.TextView
 import android.widget.Toast
 import androidx.fragment.app.Fragment
 import androidx.recyclerview.widget.LinearLayoutManager

 import com.bumptech.glide.Glide
 import com.google.firebase.database.*
 import kotlinx.android.synthetic.main.fragment_user.*
 import nguyen.luan.getcard.R
 import nguyen.luan.getcard.R.id.*
 import nguyen.luan.getcard.Utils.GooglePlayScraper
 import nguyen.luan.getcard.Utils.ScreenPreference
 import nguyen.luan.getcard.adapter.ListAppAdapter
 import nguyen.luan.getcard.model.DeviceModel
 import java.util.*
import com.google.firebase.database.DataSnapshot




/**
 * Created by PC on 12/8/2017.
 */
class ChangeCardFragment : Fragment() {

    var dialog:Dialog?=null
    private val listApp = ArrayList<DeviceModel>()
    private lateinit var databaseReference: DatabaseReference
    private lateinit var dbChild: DatabaseReference
    private lateinit var dbChild1: DatabaseReference
    private lateinit var dbChildPoint: DatabaseReference
    private var myAdapter: ListAppAdapter? = null
     var nameAppPackage: String? = null
    var tvpoint:TextView? = null
     var point = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var url = ScreenPreference.instance.saveAvatar +"?type=large"
        Glide.with(activity).load(url).error(R.drawable.ic_launcher_background).into(imageView)
        user_name.text = ScreenPreference.instance.saveName

        var firstApp =  ScreenPreference.instance.saveFirstApp
        if(!firstApp){
            val database = FirebaseDatabase.getInstance()
            val userInfo = database.getReference("User")
            userInfo.child(ScreenPreference.instance.saveEmail).child("userPoint").setValue("0")
            ScreenPreference.instance.saveFirstApp=true

        }else{
            databaseReference = FirebaseDatabase.getInstance().reference
            dbChildPoint = databaseReference.child("User").child(ScreenPreference.instance.saveEmail).child("userPoint")
            dbChildPoint.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    point = p0.value.toString().toInt()
                    tvpoint?.text = point.toString()
                }
            })
        }

        btnAdd.setOnClickListener {

            dialog = Dialog(activity)
            dialog?.setContentView(R.layout.input_dialog)
            var name = dialog?.findViewById<View>(R.id.nameApp) as EditText
            var btnSave = dialog?.findViewById<View>(R.id.btnSave) as Button
            btnSave.setOnClickListener{
                  nameAppPackage = name.text.toString()
                ScrapePlayStoreTask().execute(nameAppPackage.toString())
                dialog?.dismiss()
            }
            dialog?.show()
        }


        loadData()
    }
    private fun displayUsers(ls: List<DeviceModel>) {

        myAdapter!!.setData(ls)

    }
    private fun loadData() {
        listApp.clear()
        var dbQuery:Query?=null
        databaseReference = FirebaseDatabase.getInstance().reference
        dbChild = databaseReference.child("User").child(ScreenPreference.instance.saveEmail).child(ScreenPreference.instance.saveDeviceID)
        dbQuery = dbChild.orderByChild("point").limitToLast(10)
        dbQuery.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                listApp.clear()
                for (child in p0.children) {
                    listApp.add(child.getValue(DeviceModel::class.java)!!)
                }
                listApp.reverse()
                myAdapter!!.notifyDataSetChanged()
            }
        })

        rcvListApp.layoutManager = LinearLayoutManager(activity)
        myAdapter = ListAppAdapter(this!!.activity!!, listApp)

        rcvListApp.adapter = myAdapter
        myAdapter!!.notifyDataSetChanged()

        var dbQuery1:Query?=null
        dbChild1 = databaseReference.child("User")
        dbQuery1 = dbChild1.startAt("icon")
        dbChild1.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var b = p0.value
            }
        })

        myAdapter!!.setOnItemClickListener(object : ListAppAdapter.ClickListener {
            override fun OnItemClick(position: Int, v: View) {

            }

            override fun OnItemClickUpdate() {
                loadData()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_user, container, false)
            instance = this
        tvpoint = view.findViewById(R.id.tvPoint)
        return view
    }

    internal inner class ScrapePlayStoreTask : AsyncTask<String, Void, GooglePlayScraper.AppDetails>() {

        private var progressDialog: ProgressDialog? = null

        override fun onPreExecute() {
            progressDialog = ProgressDialog.show(activity, "Please Wait...", "Inputing Application...")
        }

        override fun doInBackground(vararg params: String): GooglePlayScraper.AppDetails? {
            try {
                return GooglePlayScraper.scrapeAppDetails(params[0])
            } catch (e: GooglePlayScraper.WebScrapeException) {
                e.printStackTrace()
                return null
            }

        }

        override fun onPostExecute(appDetails: GooglePlayScraper.AppDetails?) {
            progressDialog!!.dismiss()

            if (appDetails == null) {
                Toast.makeText(activity, "No data in Google Play", Toast.LENGTH_LONG).show()
                return
            }
            var nameApp= appDetails.title
            var icon = appDetails.icon
            var androidId =  ScreenPreference.instance.saveDeviceID
            var emailParam = ScreenPreference.instance.saveEmail
            val database = FirebaseDatabase.getInstance()
            val userInfo = database.getReference("User")
            val listApp = database.getReference("listApp")
            var alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?/.,"
            val character = (Math.random() * 26).toInt()
            val uuid = UUID.randomUUID().toString()
            val ids = alphabet.substring(character, character + 1)
            var id = ids + uuid
            println(id)

            var deviceParams = DeviceModel(nameApp, icon, "0", emailParam,androidId,id,nameAppPackage.toString())

            userInfo.child(emailParam).child(androidId).child(nameApp).setValue(deviceParams)
           // userInfo.child(emailParam).child("Pkg -$androidId").child(nameApp).setValue(nameAppPackage)
          //  userInfo.child(emailParam).child("listPkgApp -$androidId").child(nameApp).setValue(nameAppPackage)

            listApp.child("$nameApp-$androidId").setValue(deviceParams)

        }



    }
    companion object {
        lateinit var instance: ChangeCardFragment
            private set
    }

}