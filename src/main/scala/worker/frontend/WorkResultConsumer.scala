package worker.frontend

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.adapter._
import akka.cluster.pubsub.{DistributedPubSub, DistributedPubSubMediator}
import worker.WorkResult
import worker.backend.WorkManager

/**
 * @author will.109
 * @date 2020/02/19
 **/
object WorkResultConsumer {
  def apply(): Behavior[Any] =
    Behaviors.setup[Any] { ctx =>
      // FIXME use typed pub sub once https://github.com/akka/akka/issues/26338 is done
      val mediator = DistributedPubSub(ctx.system).mediator
      mediator ! DistributedPubSubMediator.Subscribe(WorkManager.ResultsTopic, ctx.self.toClassic)
      Behaviors.receiveMessage[Any] {
        case _: DistributedPubSubMediator.SubscribeAck =>
          ctx.log.info("Subscribed to {} topic", WorkManager.ResultsTopic)
          Behaviors.same
        case WorkResult(workId, result) =>
          ctx.log.info("Consumed result: {}", result)
          Behaviors.same
      }
    }

}
