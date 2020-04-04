package com.tailorfit.android.tailorfitapp.gig

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tailorfit.android.base.BaseViewModel
import com.tailorfit.android.networkutils.LoadingStatus
import com.tailorfit.android.networkutils.Result
import com.tailorfit.android.networkutils.disposeBy
import com.tailorfit.android.tailorfitapp.models.request.CreateGigRequest
import com.tailorfit.android.tailorfitapp.models.request.GigImageModel
import com.tailorfit.android.tailorfitapp.models.response.CreateGigResponse
import com.tailorfit.android.tailorfitapp.repositories.GigsRepository
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject


enum class ImageUploadStatus { NOT_UPLOADED, UPLOADING, SUCCESS, FAILED }


class GigViewModel @Inject constructor(private val gigsRepository: GigsRepository) :
    BaseViewModel() {

    private val _imagePlaceHolder = MutableLiveData<List<GigImageModel>>()
    val imagePlaceHolder: LiveData<List<GigImageModel>>
        get() = _imagePlaceHolder

    private val _gigImageUploadStatus =
        MutableLiveData<ImageUploadStatus>(ImageUploadStatus.NOT_UPLOADED)
    val imageUploadStatus: LiveData<ImageUploadStatus>
        get() = _gigImageUploadStatus

    private val _createGigResponse = MutableLiveData<CreateGigResponse>()
    val createGigResponse: LiveData<CreateGigResponse> = _createGigResponse


    fun getImagePlaceHolders() {
        Timber.d("size is ${gigsRepository.getImagePlaceHolder().size}")
        _imagePlaceHolder.value = gigsRepository.getImagePlaceHolder()
    }

    fun uploadGigStyle(
        photoUri: Uri
    ) {
        gigsRepository.uploadImage(photoUri)
    }

    fun createGig(token : String, createGigRequest: CreateGigRequest) {
        _loadingStatus.value = LoadingStatus.Loading("Creating Gig, please wait")
        gigsRepository.createGig(token, createGigRequest)
            .subscribeBy {
                when (it) {
                    is Result.Success -> {
                        _createGigResponse.value = it.data
                        _loadingStatus.value = LoadingStatus.Success
                    }
                    is Result.Error -> _loadingStatus.value =
                        LoadingStatus.Error(it.errorCode, it.errorMessage)
                }
            }.disposeBy(disposeBag)
    }


    override fun addAllLiveDataToObservablesList() {
        addAllLiveDataToObservablesList(_imagePlaceHolder, _gigImageUploadStatus)
    }


}