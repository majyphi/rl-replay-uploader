package fr.majestic.replay.uploader

import com.typesafe.scalalogging.Logger
import scalaj.http.{Http, HttpResponse, MultiPart}

object Uploader {

  val logger = Logger(this.getClass)

  def uploadReplay(replay: ReplayData)(implicit props : AutoUploaderProperties): UploadResponse = {
    logger.info("Uploading " + replay.name)
    val response = Http(BallChasingDotCom.UploadEndPoint)
      .header(BallChasingDotCom.AuthHeader, props.authKey)
      .postMulti(MultiPart("file", replay.name, "form-data", replay.fileData))
      .asString

    logger.info("Done!")

    UploadResponse(replay.name, response)
  }


  def uploadAndProcessAnswers(implicit props : AutoUploaderProperties): Unit = {
    logger.info("Uploading Replays...")
    val uploadedReplays =
      LocalIO.getReplays(props.replayFolder)
      .map(uploadReplay)


    LocalIO.saveUploadedReplays(uploadedReplays)
    ErrorReporting.reportErrors(uploadedReplays)


    logger.info("Upload Complete")


  }

}

case class ReplayData(name: String, fileData: Array[Byte])

case class UploadResponse(replayName: String, response: HttpResponse[String]) {

  def isUploaded(): Boolean = {
    response.code == 409 || response.code == 201
  }

  def isError(): Boolean = {
    response.code.toString.startsWith("4")
  }


}

object BallChasingDotCom {

  val AuthHeader = "Authorization"

  val root = "https://ballchasing.com"

  val CheckKeyEndPoint = s"$root/api/"

  val UploadEndPoint = s"$root/api/v2/upload"
}
