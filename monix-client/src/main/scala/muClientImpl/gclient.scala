package muClientImpl

import higherkindness.mu.rpc._
import cats.effect.{IO, Resource}
import higherkindness.mu.rpc.ChannelFor
import muServerImpl.CommonRuntime
import muServerImpl.GreeterServiceMonix.ProtoMonixGreeter

object gclient {

  trait Implicits extends CommonRuntime {

    val channelFor: ChannelFor = ChannelForAddress("localhost", 8080)

    implicit val serviceClient: Resource[IO, ProtoMonixGreeter[IO]] =
      ProtoMonixGreeter.client[IO](channelFor)
  }

  object implicits extends Implicits
}
