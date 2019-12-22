package fr.majestic.replay.uploader

import java.io.{BufferedWriter, File, FileWriter}
import java.nio.file.{Files, Path, Paths}

import com.typesafe.scalalogging.Logger

import scala.io.Source
import scala.util.Try

object LocalIO {

  val logger = Logger(this.getClass)

  def getListOfFiles(dir: String): List[Path] = {
    val path = Paths.get(dir)
    Files.list(path)
      .toArray
      .map(_.asInstanceOf[Path])
      .toList
  }

  def getReplays(directory: String)(implicit props : AutoUploaderProperties): List[ReplayData] = {
    val listOfAlreadyUploadedReplays =
      Try(Source.fromFile(props.uploadedList)
      .getLines())
        .getOrElse(List[String]())
      .filter(!_.startsWith("#"))
      .toList

    getListOfFiles(props.replayFolder)
      .filter(path => {
        !listOfAlreadyUploadedReplays
          .contains(path.getFileName.toString)
      })
      .map(path => {
        ReplayData(path.getFileName.toString,
          Files.readAllBytes(path))
      })
  }



  def saveUploadedReplays(replays: List[UploadResponse])(implicit props : AutoUploaderProperties): Unit = {
    val file = new File(props.uploadedList)
    val bw = new BufferedWriter(new FileWriter(file,true))

    replays
      .filter(_.isUploaded())
      .map(_.replayName)
      .foreach(name => {
        logger.info(s"\tSaving ${name} to List : ${props.uploadedList}")
        bw.newLine()
        bw.write(name)
      })

    bw.close()

  }

}
