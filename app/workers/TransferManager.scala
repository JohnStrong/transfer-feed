package workers

import akka.actor._

object TransferManager {
	def props():Props = Props(new TransferManager)
}

class TransferManager extends Actor {

	import actors.TransferFeed.{Source, Team}
	
	def receive = {
		case team @ Team(name) => println(name) // add new team to subbed list
		case source @ Source(name, url) => println(name); println(url) //add new media source
		case _ => sys.error("failed in Transfer Manager: unknown message")
	}
}