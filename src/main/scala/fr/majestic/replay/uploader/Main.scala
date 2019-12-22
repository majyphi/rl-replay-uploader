package fr.majestic.replay.uploader

import com.typesafe.scalalogging.Logger

object Main {

  val logger = Logger(this.getClass)

  def main(args: Array[String]): Unit = {

    implicit val props: AutoUploaderProperties = AutoUploaderProperties.getProperties(args)

    if(Authentication.keyIsValid){
      Uploader.uploadAndProcessAnswers
    } else {
      logger.info("Authentication failed, check Token")
    }


  }

}
