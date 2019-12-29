package fr.majestic.replay.uploader

import fr.majestic.replay.uploader.properties.AutoUploaderProperties
import org.scalatest.flatspec.AnyFlatSpec

import scala.util.Try

class PropertiesImportTest extends AnyFlatSpec {

  val propertiesTestResourcesPath = "src/test/resources/propertiesTestResources"

  "AutoUploaderProperties" should "read the application.properties file and contain the appropriate properties" in {
    val propsReadTentative =
      Try {
        AutoUploaderProperties.getProperties(Array(propertiesTestResourcesPath + "/application.properties"))
      }

    assert(propsReadTentative.isSuccess, "AutoUploaderProperties did not read the application.properties file")

    val propsToTest = propsReadTentative.get

    assert(propsToTest.authKey == "myAuthKey", "AutoUploaderProperties did not read properly the authKey")
    assert(propsToTest.replayFolder == "myReplayFolder", "AutoUploaderProperties did not read properly the replayFolder")
    assert(propsToTest.uploadedList == "myUploadedList", "AutoUploaderProperties did not read properly the uploadedList")

  }

  "AutoUploaderProperties" should "fail when information is missing" in {
    val propsReadTentativeEmpty =
      Try {
        AutoUploaderProperties.getProperties(Array.empty[String])
      }

    assert(propsReadTentativeEmpty.isFailure, "AutoUploaderProperties did not fail as expected")

    val propsReadTentativeWrongFile =
      Try {
        AutoUploaderProperties.getProperties(Array(propertiesTestResourcesPath+"/nonExistingFile"))
      }
    assert(propsReadTentativeWrongFile.isFailure, "AutoUploaderProperties did not fail as expected")

  }

}
