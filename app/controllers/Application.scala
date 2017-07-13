package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.filters.csrf._

class Application @Inject() extends Controller {

    def logout = Action { implicit request =>
        Redirect(routes.HomeController.index()).withNewSession
    }
}