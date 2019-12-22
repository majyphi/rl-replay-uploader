package fr.majestic.replay.uploader

import scalaj.http.Http

object Authentication {

  def keyIsValid(implicit props : AutoUploaderProperties): Boolean = {
    val response = Http(BallChasingDotCom.CheckKeyEndPoint)
      .header(BallChasingDotCom.AuthHeader, props.authKey)
      .asString

    response.code == 200
  }

}
