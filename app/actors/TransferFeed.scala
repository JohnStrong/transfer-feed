package actors

import akka.actor._

object TransferFeed {
	
	def props(team: String, out: ActorRef, app: ActorRef) = {
		Props(new TransferFeed(team, out, app))
	}

	case class Source(url: String)
}

class TransferFeed(team: String, out: ActorRef, app: ActorRef) extends Actor {

	import TransferFeed._

	val toWatch = context.actorOf(Watchlist.props(self), "watchlist")

	def receive = {
		case s @ Source(_) => app ! s
		case _ => sys.error("invalid message received in TransferFeed")
	}
}