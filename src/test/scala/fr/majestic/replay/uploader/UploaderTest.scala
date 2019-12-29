package fr.majestic.replay.uploader

import java.nio.file.{Files, Path, Paths}

import fr.majestic.replay.uploader.upload.{ReplayData, Uploader}
import org.scalatest.flatspec.AnyFlatSpec

import scala.io.Source

class UploaderTest extends AnyFlatSpec {


  val uploaderTestResourcesPath = "src/test/resources/uploaderTestResources"

  val uploader = Uploader(MockBackend(uploaderTestResourcesPath))


  "Uploader" should "succeed in uploading a simple replay" in {
    Files.delete(Paths.get(uploaderTestResourcesPath+"/replayToUpload"))

    val replayToUpload = ReplayData("replayToUpload", "This is the data to upload".getBytes)

    uploader.uploadReplayAndGetResponse(replayToUpload)


    val uploadedReplays = Files.list(Paths.get(uploaderTestResourcesPath))
      .toArray
      .map(_.asInstanceOf[Path])
      .toList

    assert(uploadedReplays.size == 1)
    assert(uploadedReplays.head.getFileName.toString.equals("replayToUpload"), "The uploaded replay is not the one expected")

    val fileContent = Source.fromFile(uploaderTestResourcesPath + "/replayToUpload")
    assert(fileContent.getLines().mkString.equals("This is the data to upload"), "The data uploaded does not match the replay")


  }
}
