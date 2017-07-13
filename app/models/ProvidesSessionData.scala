package models

import play.api.mvc._

trait ProvidesSessionData {

    def isConnected[A](implicit request: Request[A]): Option[String] = {
        request.session.get("user")
    }

    def isAdmin[A](implicit request: Request[A]): Boolean = {
        Some("admin") == request.session.get("user")
    }
}