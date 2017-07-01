package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.i18n._

import play.api.data._
import play.api.data.Forms._

import models.ProvidesHeader

case class UserData(name: String, age: Int)

// NOTE: Add the following to conf/routes to enable compilation of this class:
/*
GET     /user        controllers.UserController.userGet
POST    /user        controllers.UserController.userPost
*/

/**
 * User form controller for Play Scala
 */
class UserController @Inject()(implicit val messagesApi: MessagesApi) extends Controller with 
                              I18nSupport with ProvidesHeader {

  val userForm = Form(
    mapping(
      "name" -> text,
      "age" -> number
    )(UserData.apply)(UserData.unapply)
  )

  def userGet = Action { implicit request =>
  if (request.session.get("user") != None) {
      Ok(views.html.user.form(userForm))
    } else {
      Redirect(routes.HomeController.index())
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
        Redirect(routes.UserController.userGet()).flashing("success" -> ("Successful " + userData.toString))
      }
    )
  }
}
