package models

import play.api.mvc._

trait ProvidesHeader {

  implicit def header[A](implicit request: Request[A]) : Header = {
    val user = request.session.get("user")
    Header(user)
  }
}