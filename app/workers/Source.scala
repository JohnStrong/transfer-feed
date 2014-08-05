package workers

import akka.actor._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import rss.RssStream

object Source {
	def props(feed: RssStream):Props = Props(new Source(feed))

	case object Cycle
}

class Source(feed: RssStream) extends Actor with ActorLogging {

	import Source._

	// set up schedule event tick to load xml contents from stream
	val task = context.system.scheduler.schedule(0 seconds, 10 minutes, self, Cycle)

	override def postStop() = {
		task.cancel()
		log.debug("stopped source feed cycle task")
	}

	def receive = {
		case Cycle => // look up latest transfer news for source
		case _ => sys.error("unknown message in SOURCE")
	}
}