package serverImpl

import cats.effect.Sync
import muServerImpl.GreeterServiceFS2.HelloRequest
import muServerImpl.GreeterServiceFS2.HelloResponse
import muServerImpl.GreeterServiceFS2.ProtoFS2Greeter
import fs2._

class GreeterFS2ServiceHandler[F[_]: Sync] extends ProtoFS2Greeter[F] {
  override def sayHelloFS2(req: HelloRequest): fs2.Stream[F, HelloResponse] =
    Stream.emits((1 to 4).map(i => HelloResponse(req.arg1, req.arg2 + i)))
}
