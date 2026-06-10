package com.nikola0055.kmp.ui.screen

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikola0055.kmp.model.Hewan
import com.nikola0055.kmp.network.ApiStatus
import com.nikola0055.kmp.network.HewanApi
import com.nikola0055.kmp.toJpegByteArray
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Hewan>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    fun retrieveData(userId: String) {
        viewModelScope.launch {
            status.value = ApiStatus.LOADING
            try {
                data.value = HewanApi.service.getHewan(userId)
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                println("MainViewModel - Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun saveData(userId: String, nama: String, namaLatin: String, bitmap: ImageBitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val byteArray = bitmap.toJpegByteArray()

                val multipart = MultiPartFormDataContent(
                    formData {
                        append("nama", nama)
                        append("namaLatin", namaLatin)
                        append("image", byteArray, Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=\"image.jpg\""
                            )
                        })
                    }
                )

                val result = HewanApi.service.postHewan(userId, multipart)

                if (result.status == "success")
                    retrieveData(userId)
                else
                    throw Exception(result.message)
            } catch (e: Exception) {
                println("MainViewModel - Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun clearMessage() { errorMessage.value = null }
}

