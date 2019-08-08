package muServerImpl

import higherkindness.mu.rpc.protocol.{Protobuf, service}
import monix.reactive.Observable

object GreeterServiceMonix {

  final case class HelloRequest(arg1: String, arg2: String)
  final case class HelloResponse(arg1: String, arg2: String)
  @service(Protobuf) trait ProtoMonixGreeter[F[_]] {
    def sayHelloMonix(req: HelloRequest): Observable[HelloResponse]
  }

}
