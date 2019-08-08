package muServerImpl

import higherkindness.mu.rpc.protocol.{Protobuf, message, service}

object GreeterService {

  sealed trait ServerStatus
  case object NOTSERVING extends ServerStatus
  case object SERVING extends ServerStatus
  case object UNKNOWN extends ServerStatus

  @message final case class HelloRequest(arg1: String, arg2: String)
  @message final case class HelloResponse(arg1: String, arg2: String)
  @service(Protobuf) trait ProtoGreeter[F[_]] {
    def sayHelloProto(req: HelloRequest): F[HelloResponse]
  }

}
