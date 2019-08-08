import cats.effect.{ExitCode, IO, IOApp}
import io.chrisdavenport.log4cats.SelfAwareStructuredLogger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import monix.eval.Task
import monix.execution.Scheduler
import monix.reactive.{Consumer, Observable}
import muServerImpl.GreeterServiceMonix.{HelloRequest, HelloResponse}
import muClientImpl.gclient.implicits._
object ClientMain extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {

    implicit def logger: SelfAwareStructuredLogger[IO] =
      Slf4jLogger.getLogger
    import concurrent.duration._
    val consumer = Consumer
      .foreach(println)

    {
      for {
        _ <- logger.info("Starting monix client.")
        /* result <- serviceClient.use(
          _.sayHelloMonix(HelloRequest("Hello", "client"))
            .consumeWith(consumer)
            .toAsync[IO]
        )*/
        res <- Observable
          .range(0, 11)
          .map(i => HelloResponse("Good bye", "client" + i))
          .consumeWith(consumer)
          .toAsync[IO]
      } yield res
    }.attempt
      .map(_.fold(e => {
        println("Error! :" + e.getMessage)
        println("ERROR! :" + e.getStackTrace.mkString("\n"))

        ExitCode.Error
      }, _ => ExitCode.Success))
  }
}
