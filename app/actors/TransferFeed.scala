package actors

import akka.actor._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import play.api.mvc.WebSocket.FrameFormatter

object TransferFeed {
	
	def props(out: ActorRef, app: ActorRef) = {
		Props(new TransferFeed(out, app))
	}

	sealed trait StreamEvent

	case class Team(name: String, id: Int) extends StreamEvent

	case class Source(name: String, url: String) extends StreamEvent

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
		implicit def teamEventFormatter: Reads[Team] = (
			(__ \ "team").read[String] and
			(__ \ "id").read[Int]
			)(Team.apply _)
	}

	object SourceEvent {
		implicit def sourceEventFormatter: Reads[Source] = (
			(__ \ "name").read[String] and
			(__ \ "url").read[String]
		)(Source.apply _)
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
		case team @ Team(name, _) => app ! team
		case source @ Source(_, _) => app ! source
		case _ => sys.error("invalid message received in TransferFeed")
	}
}