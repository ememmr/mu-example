package clientImpl

import cats.effect.{IO, Resource}
import higherkindness.mu.rpc.ChannelFor
import muServerImpl.GreeterServiceFS2.ProtoFS2Greeter
import serverImpl.CommonRuntime
import higherkindness.mu.rpc.config.channel._

object gclient {

  trait Implicits extends CommonRuntime {

    val channelFor: ChannelFor =
      ConfigForAddress[IO]("rpc.host", "rpc.port").unsafeRunSync

    implicit val serviceClient: Resource[IO, ProtoFS2Greeter[IO]] =
      ProtoFS2Greeter.client[IO](channelFor)
  }

  object implicits extends Implicits
}
