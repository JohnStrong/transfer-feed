package workers

import akka.actor._
import akka.routing.ConsistentHashingRouter.ConsistentHashable

object TransferManager {

	import models.RssStream

	def props():Props = Props(new TransferManager)

	case class SourceHandler(name: String, feed:RssStream) extends ConsistentHashable {
		override def consistentHashKey: Any = name
	}
}

class TransferManager extends Actor {

	import TransferManager._
	
	def receive = {
		case SourceHandler(name, rssFeed) =>
			// add new source node if non already exists with this name
			val source = context.child(name).getOrElse {
				context.actorOf(Source.props(rssFeed), name)
			}
		case _ => sys.error("failed in Transfer Manager: unknown message")
	}
}