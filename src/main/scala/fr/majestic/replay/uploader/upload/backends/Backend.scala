package fr.majestic.replay.uploader.upload.backends

import scalaj.http.HttpResponse

trait Backend {

  def upload(objectName : String, objectData : Array[Byte]): HttpResponse[String]

}
