package ai.niki.task.photosharing.fcm

import ai.niki.task.photosharing.utils.LogUtils.LOGD
import ai.niki.task.photosharing.utils.LogUtils.makeLogTag
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService


/**
 * Created by sHIVAM on 2/20/2018.
 */

class MyFirebaseInstanceIdService : FirebaseInstanceIdService() {


    override fun onTokenRefresh() {
        super.onTokenRefresh()
        LOGD(TAG, "On token refresh called")
        val token = FirebaseInstanceId.getInstance().token
        sendToServer(token ?: "")
    }

    private fun sendToServer(token: String) {
        LOGD(TAG, "Registering on the FCM server with FCM token: " + token)
        FCM.instanceId = token
    }

    companion object {
        private val TAG = makeLogTag(MyFirebaseInstanceIdService::class.java)
    }
}
