package fr.majestic.replay.uploader

import java.io.{BufferedWriter, File, FileWriter}
import java.nio.file.{Files, Paths}

import fr.majestic.replay.uploader.properties.AutoUploaderProperties
import fr.majestic.replay.uploader.upload.UploadResponse
import fr.majestic.replay.uploader.write.WriteUploadList
import org.scalatest.flatspec.AnyFlatSpec
import scalaj.http.HttpResponse

import scala.io.Source

class WriteUploadListTest extends AnyFlatSpec {

  val writeResourcesPath = "src/test/resources/writeTestResources"

  implicit val props = AutoUploaderProperties("anyKey", writeResourcesPath + "/replays", writeResourcesPath + "/uploadList")

  val filePath = Paths.get(props.uploadedList)

  val uploadResponse = UploadResponse.apply(
    "JustUploadedReplay",
    HttpResponse("body", 201, Map())
  )
  val uploadResponses = List(uploadResponse)

  "WriteUploadList" should "create file with 1 entry 'JustUploadedReplay'" in {
    Files.delete(filePath)

    WriteUploadList.saveUploadedReplays(uploadResponses)

    assert(Files.exists(filePath), "WriteUploadList did not create the uploadList File")

    val listToTest = Source.fromFile(props.uploadedList).getLines().toList

    assert(listToTest.size == 1, "WriteUploadList did not write exactly 1 entry")
    assert(listToTest.head.equals("JustUploadedReplay"), "WriteUploadList did not write 'JustUploadedReplay")

  }

  "WriteUploadList" should "add to an existing file 'JustUploadedReplay'" in {
    Files.delete(filePath)
    val file = new File(props.uploadedList)
    val bw = new BufferedWriter(new FileWriter(file, true))
    bw.write("AlreadyUploadedReplay")
    bw.newLine()
    bw.close()

    WriteUploadList.saveUploadedReplays(uploadResponses)

    val listToTest = Source.fromFile(props.uploadedList).getLines().toList

    assert(listToTest.size == 2, "WriteUploadList did not add exactly 1 entry")
    assert(listToTest.contains("JustUploadedReplay"), "WriteUploadList did not write 'JustUploadedReplay'")
    assert(listToTest.contains("AlreadyUploadedReplay"), "WriteUploadList did not write 'AlreadyUploadedReplay'")

  }


}
