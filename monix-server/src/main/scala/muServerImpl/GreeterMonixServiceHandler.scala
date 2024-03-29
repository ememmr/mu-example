package muServerImpl

import cats.effect.Concurrent
import monix.reactive._
import muServerImpl.GreeterServiceMonix.{HelloResponse, ProtoMonixGreeter}

import concurrent.duration._
class GreeterMonixServiceHandler[F[_]: Concurrent]
    extends ProtoMonixGreeter[F] {
  override def sayHelloMonix(
    req: GreeterServiceMonix.HelloRequest
  ): Observable[GreeterServiceMonix.HelloResponse] =
    Observable.fromIterable(
      Iterable.tabulate(8)(i => HelloResponse("Good bye", req.arg2 + i))
    )

}
