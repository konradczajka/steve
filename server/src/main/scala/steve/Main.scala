package steve

import cats.effect.IOApp
import cats.effect.IO
import com.comcast.ip4s.{port, host}
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*
import sttp.tapir.server.ServerEndpoint

object Main extends IOApp.Simple:
  def run: IO[Unit] = EmberServerBuilder
    .default[IO]
    .withHost(host"0.0.0.0")
    .withPort(port"8080")
    .withHttpApp {
      import sttp.tapir.server.http4s.Http4sServerInterpreter

      val sl: List[ServerEndpoint[Any, IO]] = List(
        protocol.build.serverLogicSuccess { build =>
          IO.println(build).as(Hash(Array()))
        },
        protocol.run.serverLogicSuccess { hash =>
          IO.println(hash).as(SystemState(Map()))
        }
      )

      Http4sServerInterpreter[IO]().toRoutes(sl).orNotFound
    }
    .build
    .useForever
