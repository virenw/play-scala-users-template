package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import models.ProvidesHeader
import models.ProvidesSessionData
import models.AddsNoCacheHeaders
import services.UserService

class AdminController @Inject()(userService: UserService) extends Controller 
                                                        with ProvidesHeader 
                                                        with ProvidesSessionData
                                                        with AddsNoCacheHeaders {

    def listUsers = Action { implicit request =>
        if (isAdmin) {
            addNoCacheHeaders(Ok(views.html.admin.userlist(userService.getUsers())))
        } else {
            Redirect(routes.HomeController.index())
        }

    }
}