import cats.effect.{IO, Resource}
import io.grpc.{ManagedChannel, ServerServiceDefinition}
import org.scalatest._
import muServerImpl.GreeterService.{HelloRequest, HelloResponse, ProtoGreeter}
import higherkindness.mu.rpc.testing.servers.withServerChannel

class ServerTest extends FunSuite with Matchers with OneInstancePerTest {

  import muServerImpl.gserver.implicits._

  def withClient[Client, A](
    serviceDef: IO[ServerServiceDefinition],
    resourceBuilder: IO[ManagedChannel] => Resource[IO, Client]
  )(f: Client => A): A =
    withServerChannel(serviceDef)
      .flatMap(sc => resourceBuilder(IO(sc.channel)))
      .use(client => IO(f(client)))
      .unsafeRunSync()

  def sayHelloTest(requestGen: HelloRequest, expected: HelloResponse): Boolean =
    withClient(
      ProtoGreeter.bindService[IO],
      ProtoGreeter.clientFromChannel[IO](_)
    ) { _.sayHelloProto(requestGen).unsafeRunSync() == expected }

  test("Get a valid response when a proper request is passed") {
    assert(
      sayHelloTest(HelloRequest("Hi", "guys"), HelloResponse("guys?", "Hi"))
    )
  }

}
