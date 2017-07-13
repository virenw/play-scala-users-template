package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.i18n._

import play.api.data._
import play.api.data.Forms._

import play.api.db.DBApi

import models.ProvidesHeader
import models.ProvidesSessionData
import services.UserService

case class UserData(name: String, username: String, email: String, password: String)

// NOTE: Add the following to conf/routes to enable compilation of this class:
/*
GET     /user        controllers.UserController.userGet
POST    /user        controllers.UserController.userPost
*/

/**
 * User form controller for Play Scala
 */
class UserController @Inject()(userService: UserService, implicit val messagesApi: MessagesApi) 
                extends Controller with I18nSupport with ProvidesHeader with ProvidesSessionData {

  val userForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "username" -> nonEmptyText,
      "email" -> email,
      "password" -> nonEmptyText
    )(UserData.apply)(UserData.unapply)
  )

  def userGet = Action { implicit request =>
  if (isAdmin) {
      Ok(views.html.user.form(userForm))
    } else {
      Unauthorized(views.html.error("You need to be the admin to access this page!"))
    }
  }

  def userPost = Action { implicit request =>
    userForm.bindFromRequest.fold(
      formWithErrors => {
        // binding failure, you retrieve the form containing errors:
        BadRequest(views.html.user.form(formWithErrors))
      },
      userData => {
        /* binding success, you get the actual value. */       
        /* flashing uses a short lived cookie */ 
        //Redirect(routes.UserController.userGet()).flashing("success" -> ("Successful " + userData.toString))
        userService.createUser(userData)
        Redirect(routes.HomeController.index)
      }
    )
  }
}
