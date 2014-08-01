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

	sealed trait StreamEvent

	case class TeamMsg(name: String, id: Int) extends StreamEvent

	case class SourceMsg(name: String, url: String) extends StreamEvent

	// will change later
	case class TransferNews(sourceName: String, news: String) extends StreamEvent

	/*
	 * JSON deserializers for client event messages
	 */

	object StreamEvent {

		implicit def streamEventFormatter: Format[StreamEvent] = Format(
			(__ \ "event").read[String].flatMap {
				case "add-team" => TeamEvent.teamEventFormatter.map(identity)
				case "add-source" => SourceEvent.sourceEventFormatter.map(identity)
			}, Writes {
				case news: TransferNews => TransferNews.transferNewsFormatter.writes(news)
			}
		)

		implicit def eventFrameFormatter:FrameFormatter[StreamEvent] = FrameFormatter.jsonFrame.transform(
			event => Json.toJson(event),
			json => Json.fromJson[StreamEvent](json).fold(
				invalid => throw new RuntimeException("failed to format json: " + invalid),
				valid => valid
			)
		)
 	}

	object TeamEvent {
		implicit def teamEventFormatter: Reads[TeamMsg] = (
			(__ \ "name").read[String] ~
				(__ \ "id").read[Int]
		)(TeamMsg.apply _)
	}

	object SourceEvent {
		implicit def sourceEventFormatter: Reads[SourceMsg] = (
			(__ \ "name").read[String] ~
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