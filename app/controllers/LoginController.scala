package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.i18n._

import play.api.data._
import play.api.data.Forms._

import models.ProvidesHeader
import models.ProvidesSessionData
import models.AddsNoCacheHeaders
import services.UserService

case class LoginData(username: String, password: String)

// NOTE: Add the following to conf/routes to enable compilation of this class:
/*
GET     /login        controllers.LoginController.loginGet
POST    /login        controllers.LoginController.loginPost
*/

/**
 * Login form controller for Play Scala
 */
class LoginController @Inject()(userService: UserService, implicit val messagesApi: MessagesApi) 
            extends Controller 
            with I18nSupport 
            with ProvidesHeader 
            with ProvidesSessionData {

  val loginForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(LoginData.apply)(LoginData.unapply)
  )

  def loginGet = Action { implicit request =>
    if (isConnected == None) {
      Ok(views.html.login.form(loginForm))
    } else {
      Redirect(routes.HomeController.index())
    }
  }

  def loginPost = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => {
        // binding failure, you retrieve the form containing errors:
        BadRequest(views.html.login.form(formWithErrors))
      },
      loginData => {
        /* binding success, you get the actual value. */       
        /* flashing uses a short lived cookie */ 
        //Redirect(routes.LoginController.loginGet()).flashing("success" -> ("Successful " + loginData.toString))
        val result = userService.authenticate(loginData.username, loginData.password)
        if (result.isEmpty) {
          Redirect(routes.LoginController.loginGet()).flashing("failure" -> ("Failed to login as " + loginData.username))
        } else {
          Redirect(routes.HomeController.index()).withSession("user" -> loginData.username)
        }
      }
    )
  }

}
