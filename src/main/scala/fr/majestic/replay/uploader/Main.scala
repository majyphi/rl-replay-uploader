package fr.majestic.replay.uploader

import com.typesafe.scalalogging.Logger
import fr.majestic.replay.uploader.properties.AutoUploaderProperties
import fr.majestic.replay.uploader.read.ReadReplays
import fr.majestic.replay.uploader.upload.Uploader
import fr.majestic.replay.uploader.upload.backends.BallchasingDotComBackend
import fr.majestic.replay.uploader.write.WriteUploadList

object Main {

  val logger = Logger(this.getClass)

  def main(args: Array[String]): Unit = {

    implicit val props: AutoUploaderProperties = AutoUploaderProperties.getProperties(args)

    uploadtoBallChasingDotComAndProcessAnswers

  }

  def uploadtoBallChasingDotComAndProcessAnswers(implicit props: AutoUploaderProperties): Unit = {
    logger.info("Uploading Replays...")

    val uploader = Uploader(BallchasingDotComBackend(props))

    val uploadedReplays =
      ReadReplays.getReplays
        .map(uploader.uploadReplayAndGetResponse)


    WriteUploadList.saveUploadedReplays(uploadedReplays)
    ErrorReporting.reportErrors(uploadedReplays)


    logger.info("Upload Complete")


  }

}
