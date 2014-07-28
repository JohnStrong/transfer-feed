package controllers

import play.api._
import play.api.mvc._

object BowerAssets extends Controller {

  def at(file: String): Action[AnyContent] = {
  	Assets.at("/bower_components/", file)
  }

}