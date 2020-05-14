package com.tailorfit.android.tailorfitapp.repositories

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.tailorfit.android.networkutils.toResult
import com.tailorfit.android.networkutils.Result
import com.tailorfit.android.tailorfitapp.apis.TailorFitApIService
import com.tailorfit.android.tailorfitapp.models.request.CreateGigRequest
import com.tailorfit.android.tailorfitapp.models.request.GigImageModel
import com.tailorfit.android.tailorfitapp.models.response.CreateGigResponse
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class GigsRepository @Inject constructor(private val tailorFitApIService: TailorFitApIService) {

    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var downloadUrl = ""

    private val gigImageModelA = GigImageModel("1st", "a")
    private val gigImageModelB = GigImageModel("2nd", "b")
    private val gigImageModelC = GigImageModel("3rd", "c")
    private val gigImageModelD = GigImageModel("4th", "d")
    private val imageStyle = mutableListOf<String>()

    private val imagePlaceHolderList =
        listOf(gigImageModelA, gigImageModelB, gigImageModelC, gigImageModelD)

    fun getImagePlaceHolder(): List<GigImageModel> = imagePlaceHolderList

    //------uploads style images to firebase

    fun uploadImage(imageFilePath: Uri?) {

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        val ref = storageReference?.child("imageStyleUploads/${UUID.randomUUID()}")
        val imageUploadTask = ref?.putFile(imageFilePath!!)

        val urlTask = imageUploadTask?.continueWithTask(
            Continuation<UploadTask.TaskSnapshot,
                    Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            }
        )?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                downloadUrl = task.result.toString()
                imageStyle.add(downloadUrl)
                Log.d("TAG", "style size is ${imageStyle.size}")
            }
        }
    }


    fun createGig(token: String, createGigRequest: CreateGigRequest):
            Single<Result<CreateGigResponse>> {
        createGigRequest.style = imageStyle
        return tailorFitApIService.createGig(token, createGigRequest).toResult()
    }
}
