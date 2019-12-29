package fr.majestic.replay.uploader.read

import java.nio.file.{Files, Path, Paths}

import com.typesafe.scalalogging.Logger
import fr.majestic.replay.uploader.properties.AutoUploaderProperties
import fr.majestic.replay.uploader.upload.ReplayData

import scala.io.Source
import scala.util.Try

object ReadReplays {

  val logger = Logger(this.getClass)

  def getListOfFiles(dir: String): List[Path] = {
    val path = Paths.get(dir)
    Files.list(path)
      .toArray
      .map(_.asInstanceOf[Path])
      .toList
  }

  def getReplays(implicit props: AutoUploaderProperties): List[ReplayData] = {
    val uploadedReplays = getUploadedList

    getListOfFiles(props.replayFolder)
      .filterNot(replay => uploadedReplays.contains(replay.getFileName.toString))
      .map(path => {
        ReplayData(path.getFileName.toString,
          Files.readAllBytes(path))
      })
  }

  def getUploadedList(implicit props: AutoUploaderProperties): List[String] = {
    val emptyList = List[String]()

    Try{
      Source.fromFile(props.uploadedList)
        .getLines()
    }.getOrElse(emptyList)
      .filter(!_.startsWith("#"))
      .toList
  }

}
