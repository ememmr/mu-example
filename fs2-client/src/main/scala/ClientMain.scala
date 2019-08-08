import cats.effect.{ExitCode, IO, IOApp}
import io.chrisdavenport.log4cats.SelfAwareStructuredLogger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import clientImpl.gclient.implicits._
import muServerImpl.GreeterServiceFS2.HelloRequest

object ClientMain extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {

    implicit def logger: SelfAwareStructuredLogger[IO] =
      Slf4jLogger.getLogger

    {
      for {
        _ <- logger.info("Starting fs2 client.")
        result <- serviceClient.use(
          _.sayHelloFS2(HelloRequest("Hello", "client")).compile.toList
        )
        _ <- logger.info("Info gotten from server:\n" + result.mkString("\n"))
      } yield result
    }.attempt
      .map(_.fold(e => {
        logger.error(e.getMessage)
        ExitCode.Error
      }, _ => ExitCode.Success))
  }

}
