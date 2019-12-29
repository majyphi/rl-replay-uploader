package fr.majestic.replay.uploader

import java.io.{BufferedWriter, File, FileOutputStream, FileWriter}

import fr.majestic.replay.uploader.upload.backends.Backend
import scalaj.http.HttpResponse

import scala.util.{Failure, Success, Try}

case class MockBackend(saveFolder : String) extends Backend{

  override def upload(objectName: String, objectData: Array[Byte]): HttpResponse[String] = {

    val writeTentative = Try {
      val fos = new FileOutputStream(saveFolder + "/" + objectName)
      fos.write(objectData)
      fos.close()
    }

    writeTentative match {
      case Success(_) =>  HttpResponse("Success!",201,Map())
      case Failure(e) => HttpResponse(e.toString,400,Map())
    }
  }
}
