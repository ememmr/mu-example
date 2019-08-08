import cats.effect.IO
import higherkindness.mu.rpc.server.{AddService, GrpcServer}
import io.chrisdavenport.log4cats.SelfAwareStructuredLogger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger

import muServerImpl.GreeterServiceMonix.ProtoMonixGreeter

object RPCMonixServer {
  import muServerImpl.gserver.implicits._

  def main(args: Array[String]): Unit = {

    implicit def logger: SelfAwareStructuredLogger[IO] =
      Slf4jLogger.getLogger

    val runServer = for {
      _ <- logger.info("Starting monix server.")
      grpcConfig <- ProtoMonixGreeter.bindService[IO]
      server <- GrpcServer.default[IO](9000, List(AddService(grpcConfig)))
      runServer <- GrpcServer.server[IO](server)
      _ <- logger.info("Ending of monix server.")
    } yield runServer

    runServer.unsafeRunSync()
  }

}
