package controllers

import play.api._
import play.api.mvc._
import play.api.Play.current

import actors._

object Application extends Controller {

  import actors.TransferFeed._

  def index = Action {
    Ok(views.html.main())
  }

  def feed() = WebSocket.acceptWithActor[String, String] { _ => out => 
  	TransferFeed.props(out, Core.system)
  }

}