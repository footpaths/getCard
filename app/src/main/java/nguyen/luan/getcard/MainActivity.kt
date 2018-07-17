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
import com.google.firebase.database.FirebaseDatabase
import nguyen.luan.getcard.Utils.MoveScreen
import nguyen.luan.getcard.Utils.ScreenPreference
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


    }


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
