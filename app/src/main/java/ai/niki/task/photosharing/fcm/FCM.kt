package ai.niki.task.photosharing.fcm

import ai.niki.task.photosharing.model.User
import ai.niki.task.photosharing.utils.LogUtils.LOGI
import ai.niki.task.photosharing.utils.LogUtils.makeLogTag
import android.support.annotation.NonNull
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import java.util.concurrent.Executor

/**
 * Created by sHIVAM on 2/24/2018.
 */
class FCM {
    companion object {
        val TAG = makeLogTag(FCM::class.java)
        var instanceId: String? = ""

        fun addNewUserToDb(acc:GoogleSignInResult) {
            val dbRef = FirebaseFirestore.getInstance()
            val user = User(
                    uid = acc.signInAccount?.email!!,
                    admin = false,
                    deviceIds = mutableListOf(FirebaseInstanceId.getInstance().token!!),
                    email = acc.signInAccount?.email!!,
                    group = mutableListOf(""),
                    image_url = acc.signInAccount?.photoUrl!!.toString(),
                    name = acc.signInAccount?.displayName!!,
                    online = false
            )
            dbRef.collection("users")
                    .add(user)
                    .addOnSuccessListener {
                        LOGI(TAG, "DocumentSnapshot added with ID: " + it.id)
                    }
                    .addOnFailureListener {
                        Log.w(TAG, "Error adding document", it)
                    }
        }
    }


}