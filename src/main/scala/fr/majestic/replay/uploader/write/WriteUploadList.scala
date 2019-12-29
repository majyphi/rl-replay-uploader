package fr.majestic.replay.uploader.write

import java.io.{BufferedWriter, File, FileWriter}

import com.typesafe.scalalogging.Logger
import fr.majestic.replay.uploader.properties.AutoUploaderProperties
import fr.majestic.replay.uploader.upload.UploadResponse

object WriteUploadList {

  val logger = Logger(this.getClass)


  def saveUploadedReplays(replays: List[UploadResponse])(implicit props: AutoUploaderProperties): Unit = {
    val file = new File(props.uploadedList)
    val bw = new BufferedWriter(new FileWriter(file, true))

    replays
      .filter(_.isUploaded())
      .map(_.replayName)
      .foreach(name => {
        logger.info(s"\tSaving ${name} to List : ${props.uploadedList}")
        bw.write(name)
        bw.newLine()
      })

    bw.close()

  }


}
