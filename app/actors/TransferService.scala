package actors

import akka.actor._
import akka.actor.Props
import akka.routing.FromConfig

import rss._
import workers.TransferManager.SourceHandler

object TransferService {
	def props(): Props = Props(new TransferService)
}

// set up cluster
class TransferService extends Actor {

	import TransferFeed._

	var manager = context.actorOf(Props.empty.withRouter(FromConfig), "worker")

	def receive = {
		case SourceMsg(name, url) =>
			val stream = new RssStream with RssParser {
				val link = url
			}
			
			manager ! SourceHandler(name, stream)
		case _ => sys.error("invalid message received in TransferService")
	}

}