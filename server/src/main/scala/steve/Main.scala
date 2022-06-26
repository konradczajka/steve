package steve

import cats.effect.IOApp
import cats.effect.IO
import com.comcast.ip4s.{port, host}
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.http4s.Http4sServerInterpreter

object Main extends IOApp.Simple:
  def run: IO[Unit] = EmberServerBuilder
    .default[IO]
    .withHost(host"0.0.0.0")
    .withPort(port"8080")
    .withHttpApp {

      val exec = ServerSideExecutor.instance[IO]

      val sl: List[ServerEndpoint[Any, IO]] = List(
        protocol.build.serverLogicSuccess(exec.build),
        protocol.run.serverLogicSuccess(exec.run)
      )

      Http4sServerInterpreter[IO]().toRoutes(sl).orNotFound
    }
    .build
    .useForever
