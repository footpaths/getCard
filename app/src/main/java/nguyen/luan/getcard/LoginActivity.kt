package nguyen.luan.getcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.database.FirebaseDatabase
import nguyen.luan.getcard.Utils.DialogLoading
import nguyen.luan.getcard.Utils.ScreenPreference
import nguyen.luan.getcard.model.DeviceModel
import nguyen.luan.getcard.model.Emails
import androidx.core.content.ContextCompat.startActivity
import android.app.ActivityOptions


class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    var mCallbackManager: CallbackManager? = null
    var currentUser: FirebaseUser? = null
    var dialog: DialogLoading? = null
    private var firebaseAuthListener: FirebaseAuth.AuthStateListener? = null
    var androidId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_with_facebook.visibility = View.VISIBLE
        var dialog = DialogLoading
        dialog.ProgressDialogLoader(this@LoginActivity)
        mAuth = FirebaseAuth.getInstance()
        instance = this
        androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        firebaseAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            currentUser = mAuth!!.currentUser
            if (currentUser != null) {
                updateUI()
            }
        }
        mCallbackManager = CallbackManager.Factory.create()


        login_with_facebook.setReadPermissions("email", "public_profile")
        login_with_facebook.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d("kq", "facebook:onSuccess:$loginResult")
                login_with_facebook.visibility = View.GONE


                dialog.progress_dialog_creation("Loading...")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d("kq", "facebook:onCancel")
                // ...
            }

            override fun onError(error: FacebookException) {
                Log.d("kq", "facebook:onError", error)
                // ...
            }
        })

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        mCallbackManager!!.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("kq", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth!!.signInWithCredential(credential).addOnCompleteListener(this@LoginActivity, object : OnCompleteListener<AuthResult> {
            override fun onComplete(task: Task<AuthResult>) {
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("kq", "signInWithCredential:success")
                    currentUser = mAuth!!.currentUser


                    //updateUI( )
                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w(FragmentActivity.TAG, "signInWithCredential:failure", task.getException())
                    Toast.makeText(this@LoginActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    // updateUI( )
                }
            }

        })
    }

    private fun updateUI() {

        currentUser = mAuth!!.currentUser

        var email = currentUser?.email
        var emailParam = email
        emailParam = emailParam!!.replace("[-\\[\\]^/,'*:.!><~#$%=?|\"\\\\()]".toRegex(), "")
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("User")
        var deviceParams = DeviceModel("", "", "", "")

        myRef.child(emailParam).child(androidId.toString()).setValue(deviceParams)
        ScreenPreference.getInstance(this).saveName = currentUser?.displayName!!
        ScreenPreference.getInstance(this).saveEmail = emailParam
        ScreenPreference.getInstance(this).saveAvatar = currentUser?.photoUrl.toString()
        dialog?.progress_dialog_dismiss()
        // var intent = Intent(this@LoginActivity, MainActivity::class.java)
        // startActivity(intent)
        val myIntent = Intent(this@LoginActivity, MainActivity::class.java)
        val options = ActivityOptions.makeCustomAnimation(this@LoginActivity, R.anim.fadein, R.anim.fadeout)
        this@LoginActivity.startActivity(myIntent, options.toBundle())
        finish()
    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(this!!.firebaseAuthListener!!)
    }

    override fun onStop() {
        super.onStop()
        mAuth!!.removeAuthStateListener(this!!.firebaseAuthListener!!)
    }

    /**
     * lay du lieu qua lai
     */
    companion object {
        lateinit var instance: LoginActivity
            private set
    }

}
