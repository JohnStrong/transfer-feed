package actors

import akka.actor._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import play.api.mvc.WebSocket.FrameFormatter
import play.api.Logger

object TransferFeed {
	
	def props(out: ActorRef, app: ActorRef) = {
		Props(new TransferFeed(out, app))
	}

	sealed trait ClientEvent

	case class TeamMsg(name: String, id: Int) extends ClientEvent
	case class SourceMsg(name: String, url: String) extends ClientEvent
	case class TransferNews(sourceName: String, news: String) extends ClientEvent

	/*
	 * JSON deserializers for client event messages
	 */

	object ClientEvent {

		implicit val inEventFormatter:Reads[ClientEvent] = (
			(__ \ "event").read[String].flatMap {
				case "add-team" => 
					TeamEvent.teamEventFormatter.map(identity)
				case "add-source" => 
					SourceEvent.sourceEventFormatter.map(identity)
			}
		)

		implicit val inEventFormat:Format[ClientEvent] = 
			Format(inEventFormatter, Writes {
				case t:TransferNews => TransferNews.transferNewsFormatter.writes(t)
			})

		implicit val clientEventFormatter = FrameFormatter.jsonFrame[ClientEvent]
 	}

	object TeamEvent {

		implicit def teamEventFormatter: Reads[TeamMsg] = (
			(__ \ "name").read[String] and
			(__ \ "id").read[Int]
		)(TeamMsg.apply _)
	}

	object SourceEvent {
		implicit def sourceEventFormatter: Reads[SourceMsg] = (
			(__ \ "name").read[String] and
			(__ \ "url").read[String]
		)(SourceMsg.apply _)
	}

	object TransferNews {

		implicit def transferNewsFormatter: Writes[TransferNews] = (
			(__ \ "sourceName").write[String] and
			(__ \ "news").write[String]
		)(unlift(TransferNews.unapply))
	}
}

class TransferFeed(out: ActorRef, app: ActorRef) extends Actor {

	import TransferFeed._

	def receive = {
		case sourceMsg @ SourceMsg(name, url) => 
			Logger.debug("SOURCE MESSAGE FROM CLIENT: " + name + ": " + url)
			app ! sourceMsg
		case teamMsg @ TeamMsg(name, _) => 
			Logger.debug("TEAM MESSAFE FROM CLIENT: " + name)
		case _ => sys.error("invalid message received in TransferFeed")
	}
}