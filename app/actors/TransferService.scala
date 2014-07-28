package actors

import akka.actor._

import TransferFeed._

object TransferService {
	def props(): Props = Props(new TransferService)
}

// set up cluster
class TransferService extends Actor {

	def receive = {
		case Source(url) => println("url")
		case _ => sys.error("invalid message received in TransferService")
	}

}