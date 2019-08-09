package muServerImpl

import cats.effect.Sync
import muServerImpl.GreeterService.{HelloRequest, HelloResponse, ProtoGreeter}

class GreeterServiceHandler[F[_]: Sync] extends ProtoGreeter[F] {
  override def sayHelloProto(req: HelloRequest): F[HelloResponse] = {
    Sync[F].pure(HelloResponse(req.arg2 + "?", req.arg1))
  }
}
