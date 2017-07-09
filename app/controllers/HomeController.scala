package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import models.ProvidesHeader
import models.ProvidesSessionData

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller with ProvidesHeader with ProvidesSessionData {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit request =>
    //Ok(views.html.index())
    if (isConnected == None) {
      Redirect(routes.LoginController.loginGet())
    } else {
      Ok(views.html.index())
    }
  }

}
