package fr.majestic.replay.uploader

import fr.majestic.replay.uploader.properties.AutoUploaderProperties
import fr.majestic.replay.uploader.read.ReadReplays
import org.scalatest.flatspec.AnyFlatSpec

class ReadReplaysTest extends AnyFlatSpec {


  val readResourcesPath = "src/test/resources/readTestResources"


  implicit val props = AutoUploaderProperties("anyKey", readResourcesPath + "/replays", readResourcesPath + "/uploadList")

  "ReadReplays" should "report the 'AlreadyUploadedReplay' as such" in {

    assert(ReadReplays.getUploadedList
      .contains("AlreadyUploadedReplay"), "ReadReplays did not find the 'AlreadyUploadedReplay'")
  }

  "ReadReplays" should "import 'ReplayToUpload' and not 'AlreadyUploadedReplay'" in {
    val replays = ReadReplays.getReplays

    assert(replays.size == 1, "ReadReplays does not contain only one Replay")

    val replayToTest = replays.head

    assert(replayToTest.name == "ReplayToUpload", "The replay found is not the 'ReplayToUpload'")

    val textData = new String(replayToTest.fileData)

    assert(textData.equals("This data should be uploaded"), "The Data found in the file is not 'This data should be uploaded'")
  }

}
