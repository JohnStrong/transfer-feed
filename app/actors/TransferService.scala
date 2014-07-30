package actors

import akka.actor._
import akka.actor.Props
import akka.routing.FromConfig

object TransferService {
	def props(): Props = Props(new TransferService)
}

// set up cluster
class TransferService extends Actor {

	var manager = context.actorOf(Props.empty.withRouter(FromConfig), "worker")

	def receive = {
		case _ => sys.error("invalid message received in TransferService")
	}

}