package actors

import akka.actor._

object Watchlist {
	def props(userFeed: ActorRef) = Props(new Watchlist(userFeed))
}

class Watchlist(user: ActorRef) extends Actor {

	def receive = {
		case _ => println("list")
	}
}