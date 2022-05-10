package com.example.megarideapplication.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.megarideapplication.Fragments.Homefragment
import com.example.megarideapplication.Models.LoginApiModel
import com.example.megarideapplication.R
import com.example.megarideapplication.WebServices.ParameterService
import com.example.megarideapplication.utilis.AppURL
import com.example.megarideapplication.utilis.ShareMemory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.irozon.sneaker.Sneaker
import org.json.JSONException
import org.json.JSONObject
const val RC_SIGN_IN= 123

class LoginActivity : AppCompatActivity() , ParameterService.ResponseInterfaces, ParameterService.ResponseErrorInterface {
    private lateinit var signUpTv: TextView
    private lateinit var loginBtn: Button
    private lateinit var forgotPasswordTv: TextView
    private lateinit var loginEmailEditText: EditText
    private lateinit var loginPasswordEditText: EditText
    private lateinit var shareMemory: ShareMemory
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var googleView : View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //you can also use R.string.default_web_client_id
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signUpTv= findViewById(R.id.signuptv)
        loginBtn= findViewById(R.id.loginbtn)
        forgotPasswordTv= findViewById(R.id.forgotpassword)
        loginPasswordEditText= findViewById(R.id.password)
        loginEmailEditText= findViewById(R.id.emailedittext)
        googleView= findViewById(R.id.googleview)
        shareMemory= ShareMemory.mInstence
        shareMemory = ShareMemory.getmInstence()


//            if (shareMemory.userId.isNotEmpty()) {
//                val intent = Intent(this, HomeActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
        loginBtn.setOnClickListener {
            loginApiFun()
        }
        googleView.setOnClickListener {
            loginwithgoogleApi()
        }
        signUpTv.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        forgotPasswordTv.setOnClickListener {
            val intent = Intent(this, ForgotpasswordmainActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    private fun setValidation(): Boolean {

        if (loginEmailEditText.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your Email")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            //etName.requestFocus()
            return false
        }

        if (loginPasswordEditText.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your Password")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(loginEmailEditText.text.toString()).matches()) {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Valid Email")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }
        if (loginPasswordEditText.text.length<=6) {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Greater then 6 Digit Password")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }
        return true
    }

    // Api for login
    private fun loginApiFun() {
        if (setValidation()) {
            val jsonObject = JSONObject()
            val email = loginEmailEditText.text.toString()
            val password = loginPasswordEditText.text.toString()

            jsonObject.put("email", email)
            jsonObject.put("password", password)


            val parameterService = ParameterService(this, this, this)
            parameterService.getData(jsonObject, AppURL.LOGIN_URL)
        }
    }
    override fun getResponses(o: Any?) {
        val `object` = o as JSONObject
        try {
            val status = `object`.getBoolean("status")
            if (status == true) {
                val gsonBuilder = GsonBuilder()
                val gson: Gson = gsonBuilder.create()
                val userData = gson.fromJson(`object`.toString(), LoginApiModel::class.java)
                shareMemory.userId = userData.data.id
                shareMemory.userName = userData.data.name
                shareMemory.userEmail = userData.data.email
                shareMemory.profileImageUrl= userData.data.image
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                Sneaker.with(this) // Activity, Fragment or ViewGroup
                    .setMessage("Incorrect Email or Password")
                    .setDuration(2000)
                    .autoHide(true)
                    .sneakError()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
    override fun getError(o: Any?) {
    }

    private fun loginwithgoogleApi() {
        val signInIntent = mGoogleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val task =
                    GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            }
        }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(
                ApiException::class.java
            )

            val name: String? = account.displayName
            val email: String? = account.email
            val socialToken: String? = account.id

            val jsonObject = JSONObject()
            jsonObject.put("email", email)
            jsonObject.put("name", name)
            jsonObject.put("user_type", "user")
            jsonObject.put("social_key", "google")
            jsonObject.put("social_token", socialToken)


            if (socialToken!!.isEmpty()) {
                val parameterService = ParameterService(this, this, this)
                parameterService.getData(jsonObject, AppURL.LOGINWITHGOOGLE_URL)
            } else {
                val parameterService = ParameterService(this, this, this)
                parameterService.getData(jsonObject, AppURL.LOGINWITHGOOGLE_URL)

            }
        } catch (e: ApiException) {
            // Sign in was unsuccessful
        }
    }

    override fun onStart() {
        super.onStart()
        if (shareMemory.userId.isNotEmpty()){
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}