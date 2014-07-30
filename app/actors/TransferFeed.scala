package actors

import akka.actor._

object TransferFeed {
	
	def props(out: ActorRef, app: ActorRef) = {
		Props(new TransferFeed(out, app))
	}

	case class Team(name: String)

	case class Source(name: String, url: String)
}

class TransferFeed(out: ActorRef, app: ActorRef) extends Actor {

	import TransferFeed._

	def receive = {
		case team @ Team(name) => app ! team
		case source @ Source(_, _) => app ! source
		case _ => sys.error("invalid message received in TransferFeed")
	}
}