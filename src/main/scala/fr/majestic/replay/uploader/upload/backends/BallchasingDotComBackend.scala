package fr.majestic.replay.uploader.upload.backends

import fr.majestic.replay.uploader.properties.AutoUploaderProperties
import scalaj.http.{Http, HttpResponse, MultiPart}

class BallchasingDotComBackend(authKey: String) extends Backend {

  override def upload(objectName: String, objectData: Array[Byte]): HttpResponse[String] = {
    Http(BallchasingDotComBackend.UploadEndPoint)
      .header(BallchasingDotComBackend.AuthHeader, authKey)
      .postMulti(MultiPart("file", objectName, "form-data", objectData))
      .asString
  }

}

object BallchasingDotComBackend {

  val AuthHeader = "Authorization"

  val root = "https://ballchasing.com"

  val CheckKeyEndPoint = s"$root/api/"

  val UploadEndPoint = s"$root/api/v2/upload"

  def apply(props: AutoUploaderProperties): BallchasingDotComBackend = {

    if (keyIsValid(props.authKey)) new BallchasingDotComBackend(props.authKey)
    else {
      throw new Exception("Invalid Authentication Key")
    }

  }

  def keyIsValid(authKey: String): Boolean = {
    val response = Http(BallchasingDotComBackend.CheckKeyEndPoint)
      .header(BallchasingDotComBackend.AuthHeader, authKey)
      .asString

    response.code == 200
  }
}
