package controllers

import play.api.mvc._

import actors._

object Templates extends Controller {

  def at(file: String): Action[AnyContent] = {
  	Assets.at(path = "/public/angular-templates", file, true)
  }

}