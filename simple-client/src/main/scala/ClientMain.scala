import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.jmx.JmxReporter
import higherkindness.mu.rpc.dropwizard.DropWizardMetrics
import io.chrisdavenport.log4cats.SelfAwareStructuredLogger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import muServerImpl.GreeterService.{HelloRequest, SERVING}
import muClienteImpl.gclient.implicits._
object ClientMain extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {

    implicit def logger: SelfAwareStructuredLogger[IO] =
      Slf4jLogger.getLogger

    val registry: MetricRegistry = new MetricRegistry()

    val jmxReporter = JmxReporter.forRegistry(registry)
    jmxReporter.build().start()

    val dwMetrics = DropWizardMetrics[IO](registry, "fs2-server-report")

    val serverRes = for {
      _ <- logger.info("Starting client part")
      result <- serviceClient
        .use(_.sayHelloProto(HelloRequest("Hi", "Client")))

    } yield result

    serverRes
      .flatMap { result =>
        logger.info(s"Result = $result")
      }
      .attempt
      .map(_.fold(e => {
        println(e.getMessage)
        println(e.getStackTrace.mkString("\n"))
        ExitCode.Error
      }, _ => {
        ExitCode.Success
      }))
  }

}
