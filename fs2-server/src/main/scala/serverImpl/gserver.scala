package muServerImpl

import cats.effect.IO
import io.prometheus.client.CollectorRegistry
import muServerImpl.GreeterServiceFS2.ProtoFS2Greeter
import serverImpl.{CommonRuntime, GreeterFS2ServiceHandler}
import org.http4s.metrics.prometheus.PrometheusExportService
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.implicits._

object gserver {

  trait Implicits extends CommonRuntime {

    implicit val greeterServiceHandler: ProtoFS2Greeter[IO] =
      new GreeterFS2ServiceHandler[IO]

    def exportMetrics(cr: CollectorRegistry, port: Int, host: String) =
      BlazeServerBuilder[IO]
        .bindHttp(port, host)
        .withHttpApp(PrometheusExportService[IO](cr).routes.orNotFound)
        .serve
  }

  object implicits extends Implicits

}
