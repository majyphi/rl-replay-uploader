package fr.majestic.replay.uploader

import com.typesafe.scalalogging.Logger

object ErrorReporting {

  val logger = Logger(this.getClass)

  def reportErrors(replays: List[UploadResponse]): Unit = {
    logger.info("Checking errors")

    val errors = replays
      .filter(_.isError())

    if (errors.isEmpty) {
      logger.info("No errors!")
    } else {
      errors
        .foreach(uploadResponse => {
          logger.error(
            s"""Error returned for ${uploadResponse.replayName} with following information :${uploadResponse.response.body}""")
        })
    }
  }

}
