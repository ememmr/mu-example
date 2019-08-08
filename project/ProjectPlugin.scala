import higherkindness.mu.rpc.idlgen.IdlGenPlugin.autoImport._
import org.scalafmt.sbt.ScalafmtPlugin.autoImport._
import sbt.Keys.{libraryDependencies, _}
import sbt.{Def, _}

object ProjectPlugin extends AutoPlugin {

  override def trigger: PluginTrigger = allRequirements

  object autoImport {

    lazy val V = new {
      val log4cats = "0.3.0"
      val logbackClassic = "1.2.3"
      val muVersion = "0.18.3"
      val catsEffect = "1.2.0"
      val pureconfig = "0.10.2"
      val fs2 = "1.0.0"
      val monix = "3.0.0-RC2"
      val http4sVersion = "0.20.0"
    }
  }
  import autoImport._

  lazy val catsSettings: Seq[Def.Setting[_]] = Seq(
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % V.catsEffect,
      "org.typelevel" %% "cats-core" % V.catsEffect
    )
  )

  lazy val logSettings: Seq[Def.Setting[_]] = Seq(
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % V.logbackClassic,
      "io.chrisdavenport" %% "log4cats-core" % V.log4cats,
      "io.chrisdavenport" %% "log4cats-slf4j" % V.log4cats
    )
  )
  lazy val testingSettings = Seq(
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.6" % Test,
      "io.higherkindness" %% "mu-rpc-testing" % V.muVersion
    )
  )
  lazy val configMuSettings = Seq(
    libraryDependencies ++= Seq(
      "io.higherkindness" %% "mu-config" % V.muVersion
    )
  )

  lazy val fs2Settings: Seq[Def.Setting[_]] = Seq(
    libraryDependencies ++= Seq(
      "co.fs2" %% "fs2-core" % V.fs2,
      "io.higherkindness" %% "mu-rpc-fs2" % V.muVersion
    )
  )

  lazy val monixSettings: Seq[Def.Setting[_]] = Seq(
    libraryDependencies ++= Seq(
      "io.monix" %% "monix" % V.monix,
      "io.higherkindness" %% "mu-rpc-monix" % V.muVersion
    )
  )

  lazy val clientNettySettings: Seq[Def.Setting[_]] = Seq(
    libraryDependencies ++= Seq(
      "io.higherkindness" %% "mu-rpc-netty" % V.muVersion
    )
  )

  lazy val clientOkhttpSettings: Seq[Def.Setting[_]] = Seq(
    libraryDependencies ++= Seq(
      "io.higherkindness" %% "mu-rpc-okhttp" % V.muVersion
    )
  )

  lazy val serverSettings: Seq[Def.Setting[_]] = Seq(
    idlType := "proto", //For IDL plugin
    sourceGenerators in Compile += (Compile / srcGen).taskValue, //For IDL plugin
    libraryDependencies ++= Seq(
      "io.higherkindness" %% "mu-rpc-server" % V.muVersion, //server bootstraping
      "io.higherkindness" %% "mu-rpc-channel" % V.muVersion
    )
  )

  lazy val jodatimeMarshallerSettings: Seq[Def.Setting[_]] = Seq(
    libraryDependencies ++= Seq(
      "io.higherkindness" %% "mu-rpc-marshallers-jodatime" % V.muVersion
    )
  )

  lazy val prometheusSettings = Seq(
    libraryDependencies ++= Seq(
      "io.higherkindness" %% "mu-rpc-prometheus" % V.muVersion
    )
  )
  lazy val htt4sSettings = Seq(
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-prometheus-metrics" % V.http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % V.http4sVersion, //para mostrar las metricas de prometheus
      "org.http4s" %% "http4s-blaze-server" % V.http4sVersion //para mostrar las metricas de prometheus
    )
  )

  lazy val dropwizardSettings = Seq(
    libraryDependencies ++= Seq(
      "io.higherkindness" %% "mu-rpc-dropwizard" % V.muVersion,
      "io.dropwizard.metrics" % "metrics-jmx" % "4.0.5" // requerido para ver las metricas con el jmx)
    )
  )
  override def projectSettings: Seq[Def.Setting[_]] =
    Seq(
      version := "0.1",
      scalaVersion := "2.12.8",
      scalacOptions := Seq("-Ypartial-unification"),
      scalafmtCheck := true,
      addCompilerPlugin(
        "org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.patch
      )
    )
}
