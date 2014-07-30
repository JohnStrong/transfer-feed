package actors

import play.api._
import play.api.libs.concurrent.Akka
import akka.cluster.Cluster

import workers.TransferManager

object Core {
	
	def system(implicit app: Application) = {
		
		val core = app.plugin[CoreSystem].getOrElse { 
			sys.error("no actor system found in service ")
		}

		core.transferService
	}
}

class CoreSystem(app: Application) extends Plugin {

	private def system = Akka.system(app)

	override def onStart() = {
		system.actorOf(TransferManager.props(), "transferManager")
	}

 	lazy val transferService = system.actorOf(TransferService.props(), "transferService")
}