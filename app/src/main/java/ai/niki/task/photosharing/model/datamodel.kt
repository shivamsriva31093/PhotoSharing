package ai.niki.task.photosharing.model

import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

/**
 * Created by sHIVAM on 2/25/2018.
 */
data class User(
        val uid:String,
        val admin:Boolean = false,
        val deviceIds: List<String>,
        val email:String,
        val group: List<String>?,
        val image_url: String = "",
        val name: String,
        val online:Boolean
)