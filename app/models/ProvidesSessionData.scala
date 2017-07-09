package models

import play.api.mvc._

trait ProvidesSessionData {

    def isConnected[A](implicit request: Request[A]): Option[String] = {
        request.session.get("user")
    }
}