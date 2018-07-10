package nguyen.luan.getcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.internal.FirebaseAppHelper.getToken
import com.google.firebase.auth.AuthCredential
import androidx.annotation.NonNull


class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    var mCallbackManager: CallbackManager? = null
    var currentUser: FirebaseUser? = null
    private var firebaseAuthListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        firebaseAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = mAuth!!.currentUser
            if (user != null) {
                updateUI()
            }
        }
        mCallbackManager = CallbackManager.Factory.create()


        login_with_facebook.setReadPermissions("email", "public_profile")
        login_with_facebook.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d("kq", "facebook:onSuccess:$loginResult")
                updateUI()
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
                    val user = mAuth!!.currentUser

                    // updateUI( )
                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w(FragmentActivity.TAG, "signInWithCredential:failure", task.getException())
                    Toast.makeText(this@LoginActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    // updateUI( )
                }
            }
            /* fun onComplete(task: Task<AuthResult>) {
                 if (task.isSuccessful()) {
                     // Sign in success, update UI with the signed-in user's information
                     Log.d("kq", "signInWithCredential:success")
                     val user = mAuth!!.getCurrentUser()
                     updateUI(user)
                 } else {
                     // If sign in fails, display a message to the user.
                     //Log.w(FragmentActivity.TAG, "signInWithCredential:failure", task.getException())
                     Toast.makeText(this@MainActivity, "Authentication failed.",
                             Toast.LENGTH_SHORT).show()
                    // updateUI(null)
                 }

                 // ...
             }*/
        })
    }

    private fun updateUI() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
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

}
