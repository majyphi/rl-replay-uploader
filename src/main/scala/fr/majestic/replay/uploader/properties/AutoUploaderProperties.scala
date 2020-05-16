package fr.majestic.replay.uploader.properties

import java.io.FileInputStream
import java.util.Properties

object AutoUploaderProperties {

  def getProperties(args: Array[String]): AutoUploaderProperties = {

    if (args.isEmpty) throw new Exception("You need to specify a properties file!")

    try {

      val props = new Properties()

      props.load(new FileInputStream(readPropertiesPath(args(0))))


      val authKey = props.getProperty("auth.key")

      val replayFolder = props.getProperty("replay.folder")

      val uploadedList = props.getProperty("uploaded.list")

      AutoUploaderProperties(authKey, replayFolder, uploadedList)

    } catch {
      case e: Throwable => throw new Exception("Could not load application.properties", e)
    }
  }

  def readPropertiesPath(path: String): String = {
    if (path.startsWith("/")) path
    else {
      System.getProperty("user.dir") + "/" + path
    }
  }


}

case class AutoUploaderProperties(authKey: String, replayFolder: String, uploadedList: String)
