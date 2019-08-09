import cats.effect.IO
import higherkindness.mu.rpc.server.metrics.MetricsServerInterceptor
import higherkindness.mu.rpc.server.{AddService, GrpcServer}
import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.jmx.JmxReporter
import higherkindness.mu.rpc.dropwizard._
import higherkindness.mu.rpc.internal.interceptors.GrpcMethodInfo
import higherkindness.mu.rpc.server._
import higherkindness.mu.rpc.server.interceptors.implicits._
import muServerImpl.GreeterService.ProtoGreeter

object RPCServer {

  import muServerImpl.gserver.implicits._

  def main(args: Array[String]): Unit = {
    val registry: MetricRegistry = new MetricRegistry()

    val jmxReporter = JmxReporter.forRegistry(registry)
    jmxReporter.build().start()

    val runServer = for {
      _ <- IO.delay(println("Starting server part"))
      service <- ProtoGreeter.bindService[IO]
      dwMetrics = DropWizardMetrics[IO](registry, "traffic-report")
      grpcConfigDw = AddService(
        service.interceptWith(
          MetricsServerInterceptor(dwMetrics, Some("traffic-metrics"))
        )
      )
      server <- GrpcServer
        .default[IO](8080, List(AddService(service), grpcConfigDw))
      runServer <- GrpcServer.server[IO](server)
    } yield runServer

    runServer.unsafeRunSync()
  }

}
