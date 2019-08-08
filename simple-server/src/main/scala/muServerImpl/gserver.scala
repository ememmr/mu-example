package muServerImpl

import cats.effect.IO
import muServerImpl.GreeterService.ProtoGreeter

object gserver {

  trait Implicits extends CommonRuntime {

    implicit val greeterServiceHandler: ProtoGreeter[IO] =
      new GreeterServiceHandler[IO]

  }

  object implicits extends Implicits

}
