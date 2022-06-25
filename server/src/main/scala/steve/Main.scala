package steve

import cats.effect.IOApp
import cats.effect.IO
import com.comcast.ip4s.{port, host}
import org.http4s.ember.server.EmberServerBuilder

object Main extends IOApp.Simple:
  def run: IO[Unit] = EmberServerBuilder
    .default[IO]
    .withHost(host"0.0.0.0")
    .withPort(port"8080")
    .build
    .useForever
