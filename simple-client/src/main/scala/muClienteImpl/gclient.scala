package muClienteImpl

import cats.effect.{IO, Resource}
import higherkindness.mu.rpc.{ChannelFor, ChannelForAddress}
import muServerImpl.GreeterService.ProtoGreeter
import muServerImpl._ //Se ha generado una dependencia entre el modulo client con el modulo server

object gclient {

//  lazy val cr: CollectorRegistry = new CollectorRegistry()

  trait Implicits extends CommonRuntime {

    val channelFor: ChannelFor = ChannelForAddress("localhost", 8080)

    val serviceClient: Resource[IO, ProtoGreeter[IO]] =
      ProtoGreeter.client[IO](channelFor)
  }

  object implicits extends Implicits
}
