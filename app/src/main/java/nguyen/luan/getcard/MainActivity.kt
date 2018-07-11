package nguyen.luan.getcard

import android.app.PendingIntent.getActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.content.pm.PackageManager
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FirebaseAuth
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import nguyen.luan.getcard.Utils.MoveScreen
import nguyen.luan.getcard.fragment.ChangeCardFragment
import nguyen.luan.getcard.fragment.ReceivePointsFragment
import nguyen.luan.getcard.fragment.SpinFragment


class MainActivity : AppCompatActivity() {


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
               // moveScreen = MoveScreen(this@MainActivity)
                moveScreen!!.firstMoveFragment(R.id.content, ReceivePointsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                //moveScreen = MoveScreen(this@MainActivity)
                moveScreen!!.firstMoveFragment(R.id.content, SpinFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
              //  moveScreen = MoveScreen(this@MainActivity)
                moveScreen!!.firstMoveFragment(R.id.content, ChangeCardFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    private var moveScreen: MoveScreen? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moveScreen = MoveScreen(this@MainActivity)
        moveScreen!!.firstMoveFragment(R.id.content, ReceivePointsFragment())


      navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
//
//        val isAppInstalled = appInstalledOrNot("biz.gina.southernbreezetour")
//
//        if (isAppInstalled) {
//            //This intent will help you to launch if the package is already installed
//            val LaunchIntent = packageManager
//                    .getLaunchIntentForPackage("biz.gina.southernbreezetour")
//            startActivity(LaunchIntent)
//
//            Log.i("kq","Application is already installed.")
//        } else {
//            // Do whatever we want to do if application not installed
//            // For example, Redirect to play store
//
//            Log.i("kq","Application is not currently installed.")
//        }

        /**
         *  todo genderate key hash
         */
        /*  try {
              val info = this.packageManager.getPackageInfo(
                      "nguyen.luan.getcard",
                      PackageManager.GET_SIGNATURES)
              for (signature in info.signatures) {
                  val md = MessageDigest.getInstance("SHA")
                  md.update(signature.toByteArray())
                  Log.d("KeyHash", "KeyHash:" + Base64.encodeToString(md.digest(),
                          Base64.DEFAULT))
                  //Toast.makeText(this.applicationContext, Base64.encodeToString(md.digest(), Base64.DEFAULT), Toast.LENGTH_LONG).show()
              }
          } catch (e: PackageManager.NameNotFoundException) {

          } catch (e: NoSuchAlgorithmException) {

          }*/


    }

//
//    private fun appInstalledOrNot(uri: String): Boolean {
//        val pm = packageManager
//        try {
//            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
//            return true
//        } catch (e: PackageManager.NameNotFoundException) {
//        }
//
//        return false
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.menu_main_setting -> {
                 return true
            }
            R.id.menu_main_singout -> {
                FirebaseAuth.getInstance().signOut()
                LoginManager.getInstance().logOut()
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
                 return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
