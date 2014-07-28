package actors

import akka.actor._

object TransferService {
	def props(): Props = Props(new TransferService)
}

// set up cluster
class TransferService extends Actor {

	def receive = {
		case _ => println("received")
	}

}