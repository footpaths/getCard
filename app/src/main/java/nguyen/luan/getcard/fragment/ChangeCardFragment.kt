package nguyen.luan.getcard.fragment

 import android.app.Dialog
 import android.os.AsyncTask
 import android.app.ProgressDialog
 import android.os.Bundle
 import android.provider.Settings

 import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
 import android.widget.Button
 import android.widget.EditText
 import android.widget.Toast
 import androidx.fragment.app.Fragment

 import com.bumptech.glide.Glide
 import com.google.firebase.database.FirebaseDatabase
 import kotlinx.android.synthetic.main.fragment_user.*
 import nguyen.luan.getcard.R
 import nguyen.luan.getcard.Utils.GooglePlayScraper
 import nguyen.luan.getcard.Utils.ScreenPreference
 import nguyen.luan.getcard.model.DeviceModel


/**
 * Created by PC on 12/8/2017.
 */
class ChangeCardFragment : Fragment(), View.OnClickListener {
    var dialog:Dialog?=null
    override fun onClick(p0: View?) {



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var url = ScreenPreference.instance.saveAvatar +"?type=large"
        Glide.with(activity).load(url).error(R.drawable.ic_launcher_background).into(imageView)
        user_name.text = ScreenPreference.instance.saveName
        btnAdd.setOnClickListener {
//            ScrapePlayStoreTask().execute("biz.gina.southernbreezetour")
            dialog = Dialog(activity)
            dialog?.setContentView(R.layout.input_dialog)
            var name = dialog?.findViewById<View>(R.id.nameApp) as EditText
            var btnSave = dialog?.findViewById<View>(R.id.btnSave) as Button
            btnSave.setOnClickListener{
                var nameApp = name.text
                ScrapePlayStoreTask().execute(nameApp.toString())
                dialog?.dismiss()
            }
            dialog?.show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_user, container, false)

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
            val myRef = database.getReference("User")
            var deviceParams = DeviceModel(nameApp, icon, "0", "1")

            myRef.child(emailParam).child(androidId).setValue(deviceParams)
        }

    }


}