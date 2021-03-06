package omeronce.android.emptyproject.scannovate.camera.datasource

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import omeronce.android.emptyproject.model.network.ApiGateway
import omeronce.android.emptyproject.model.network.WebApi


class RemoteRequestDataSource(private val webApi: WebApi, private val apiGateway: ApiGateway): RequestDataSource {

    override suspend fun getJson(flowId: String, byteArray: ByteArray) =
        apiGateway.getStringResult { webApi.getJson(toMultipartText(flowId),toMultiPartFile(byteArray = byteArray)) }

    private fun toMultiPartFile(
        name: String = "upload",
        byteArray: ByteArray
    ): MultipartBody.Part {
        val reqFile = RequestBody.create(MediaType.parse("image/*"), byteArray)
        return MultipartBody.Part.createFormData(
            name,
            null,  // filename, this is optional
            reqFile
        )
    }

    private fun toMultipartText(text: String) = RequestBody.create(MediaType.parse("text/plain"), text)
}