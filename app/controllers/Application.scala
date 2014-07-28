package controllers

import play.api._
import play.api.mvc._
import play.api.Play.current

import actors._

object Application extends Controller {

  def index = Action {
    Ok(views.html.main())
  }

  def feed(team: String) = WebSocket.acceptWithActor[String, String] { _ => out => 
  	TransferFeed.props(team, out, Core.system)
  }

}