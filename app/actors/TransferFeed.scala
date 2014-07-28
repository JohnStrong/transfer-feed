package actors

import akka.actor._

object TransferFeed {
	def props(team: String, out: ActorRef, app: ActorRef) = {
		Props(new TransferFeed(team, out, app))
	}
}

class TransferFeed(team: String, out: ActorRef, app: ActorRef) extends Actor {

	val toWatch = context.actorOf(Watchlist.props(self), "watchlist")

	def receive = {
		case _ => println("feed")
	}
}