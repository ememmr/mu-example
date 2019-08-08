import cats.effect.IO
import higherkindness.mu.rpc.server.{AddService, GrpcServer}
import io.chrisdavenport.log4cats.SelfAwareStructuredLogger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import io.prometheus.client.CollectorRegistry
import fs2._
import muServerImpl.GreeterServiceFS2.ProtoFS2Greeter

object RPCFS2Server {

  import muServerImpl.gserver.implicits._

  def main(args: Array[String]): Unit = {

    implicit def logger: SelfAwareStructuredLogger[IO] =
      Slf4jLogger.getLogger

    lazy val cr: CollectorRegistry = new CollectorRegistry()

    val runServer = for {
      _ <- logger.info("Starting fs2 server.")
      service <- ProtoFS2Greeter.bindService[IO]
      // metricsOps <- PrometheusMetrics.build[IO](cr, "fs2_server_report")
      // grpcConfig: AddService = AddService(
      //   service
      //     .interceptWith(MetricsServerInterceptor(metricsOps, Some("metrics")))
      // )
      server <- GrpcServer
        .default[IO](8080, List(AddService(service))) //, grpcConfig))
      runServer <- GrpcServer.server[IO](server)
    } yield runServer

    runServer.unsafeRunSync()
    //http://localhost:9001/metrics
    //Stream(Stream.eval(runServer), exportMetrics(cr, 9001, "localhost")).parJoinUnbounded.compile.drain
    //  .unsafeRunSync()
  }

}
