package worker

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior}
import akka.cluster.typed.Cluster
import com.typesafe.config.ConfigFactory
import worker.frontend.{FrontEnd, WorkResultConsumer}

/**
 * @author will.109
 * @date 2020/02/19
 **/
object Main {
  val frontEndPortRange = 3000 to 3999

  def main(args: Array[String]): Unit = {
    val port = getPort(args.headOption)
    val config = ConfigFactory.parseString(
      s"""
      akka.remote.artery.canonical.port=$port
    """).withFallback(ConfigFactory.load())

    ActorSystem[Nothing](Guardian(), "ClusterSystem", config)
  }

  private def getPort(maybePort: Option[String]): Int = maybePort match {
    case Some(portString) if portString.matches("""\d+""") =>
      val port = portString.toInt
      if (frontEndPortRange.contains(port)) {
        port
      } else {
        frontEndPortRange.head
      }
    case _ => frontEndPortRange.head
  }
}

object Guardian {
  def apply(): Behavior[Nothing] = {
    Behaviors.setup[Nothing](ctx => {
      val cluster = Cluster(ctx.system)
      ctx.spawn(FrontEnd(), "front-end")
      ctx.spawn(WorkResultConsumer(), "consumer")
      Behaviors.empty
    })
  }
}
