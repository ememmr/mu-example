package muServerImpl

trait CommonRuntime {

  val EC: scala.concurrent.ExecutionContext =
    scala.concurrent.ExecutionContext.Implicits.global

  implicit val S: monix.execution.Scheduler =
    monix.execution.Scheduler.Implicits.global

  implicit val timer: cats.effect.Timer[cats.effect.IO] =
    cats.effect.IO.timer(EC)
  implicit val cs: cats.effect.ContextShift[cats.effect.IO] =
    cats.effect.IO.contextShift(EC)

}
