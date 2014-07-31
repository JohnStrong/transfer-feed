package workers

import akka.actor._

import models.RssStream

object Source {
	def props(feed: RssStream):Props = Props(new Source(feed))

	case object Cycle
}

class Source(feed: RssStream) extends Actor {

	import Source._

	// set up schedule event tick

	def receive = {
		case Cycle => // look up latest transfer news for source
		case _ => sys.error("unknown message in SOURCE")
	}
}