package ai.niki.task.photosharing.ui

import ai.niki.task.photosharing.BuildConfig
import ai.niki.task.photosharing.R
import ai.niki.task.photosharing.fcm.FCM
import ai.niki.task.photosharing.signin.SignInListener
import ai.niki.task.photosharing.signin.SignInManager
import ai.niki.task.photosharing.utils.AccountUtils
import ai.niki.task.photosharing.utils.LogUtils.*
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Status

class MainActivity : AppCompatActivity(), SignInListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.sign_in_button)
    lateinit var signInButton: SignInButton

    lateinit var signInManager: SignInManager
    lateinit var mGoogleApiClient:GoogleApiClient
    /**
     * Used when sign out is initiated and GoogleApiClient isn't connected.
     */
    private var mSignOutPending = false

    @OnClick(R.id.sign_in_button)
    fun onLoginButtonClick() {
        signIn()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(AccountUtils.hasActiveAccount(this)) {

        }
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        signInManager = SignInManager(this, this, getGoogleClient())
    }

    private fun getGoogleClient() : GoogleApiClient {
        val googleSignInOptions = SignInManager.getGoogleSignInOptions(BuildConfig.WEB_CLIENT_ID)
        mGoogleApiClient =GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(
                Auth.GOOGLE_SIGN_IN_API, googleSignInOptions
        ).build()
        return mGoogleApiClient
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        signInManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onConnected(p0: Bundle?) {
        LOGD(TAG, "GoogleApiClient connected");
        if (mSignOutPending) {
            signInManager.signOut()
            mSignOutPending = false
        }
    }

    override fun onConnectionSuspended(i: Int) {
        LOGW(TAG, "GoogleApiClient suspended")
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        LOGW(TAG, "onConnectionFailed")
        // Anything resolvable is automatically resolved by automanage. Failure is not resolvable.
        Toast.makeText(this, "Failed to connect to google play services.",
                Toast.LENGTH_LONG).show()
    }

    override fun onSignIn(result: GoogleSignInResult?) {
        LOGI(TAG, "successfully signed in via google " + result?.signInAccount?.displayName)
        FCM.addNewUserToDb(result!!)
    }

    override fun onSignOut(status: Status?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSignInFailed(result: GoogleSignInResult?) {
        LOGI(TAG, "sign in via google failed")
    }

    override fun onSignOutFailed(status: Status?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun signIn() {
        signInManager.signIn()
    }

    fun signOut() {
        if (!mGoogleApiClient.isConnected) {
            mSignOutPending = true
            mGoogleApiClient.connect()
        }
        signInManager.signOut()
    }

    companion object {
        val TAG = makeLogTag(MainActivity::class.java)
    }
}
