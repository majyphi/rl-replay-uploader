package fr.majestic.replay.uploader.upload

import com.typesafe.scalalogging.Logger
import fr.majestic.replay.uploader.upload.backends.Backend
import scalaj.http.HttpResponse

case class Uploader(backend: Backend) {

  val logger = Logger(this.getClass)

  def uploadReplayAndGetResponse(replay: ReplayData): UploadResponse = {
    logger.info("Uploading " + replay.name)

    val response = backend.upload(replay.name, replay.fileData)

    logger.info("Done!")

    UploadResponse(replay.name, response)
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


