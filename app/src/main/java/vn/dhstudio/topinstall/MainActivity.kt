package vn.dhstudio.topinstall

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.facebook.login.LoginManager
import vn.dhstudio.topinstall.Utils.MoveScreen
import vn.dhstudio.topinstall.fragment.ChangeCardFragment
import vn.dhstudio.topinstall.fragment.ReceivePointsFragment
import vn.dhstudio.topinstall.fragment.SpinFragment
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric


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
                moveScreen!!.firstMoveFragment(R.id.content, ChangeCardFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
              //  moveScreen = MoveScreen(this@MainActivity)

                moveScreen!!.firstMoveFragment(R.id.content, SpinFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    private var moveScreen: MoveScreen? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_main)

        moveScreen = MoveScreen(this@MainActivity)
        moveScreen!!.firstMoveFragment(R.id.content, ReceivePointsFragment())



      navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return true
    }
}
