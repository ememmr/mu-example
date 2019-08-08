package muServerImpl

import cats.effect.IO
import muServerImpl.GreeterServiceMonix.ProtoMonixGreeter

object gserver {

  trait Implicits extends CommonRuntime {

    implicit val greeterServiceHandler: ProtoMonixGreeter[IO] =
      new GreeterMonixServiceHandler[IO]

  }

  object implicits extends Implicits

}
