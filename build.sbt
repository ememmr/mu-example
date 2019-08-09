import ProjectPlugin._

lazy val server: Project = project
  .in(file("simple-server"))
  .settings(catsSettings)
  .settings(logSettings)
  .settings(serverSettings)
  .settings(testingSettings)
  .settings(dropwizardSettings)
  .settings(htt4sSettings)

lazy val client: Project = project
  .in(file("simple-client"))
  .settings(catsSettings)
  .settings(logSettings)
  .settings(clientNettySettings)
  .settings(dropwizardSettings)
  .settings(htt4sSettings)
  .dependsOn(server)

lazy val fs2server: Project = project
  .in(file("fs2-server"))
  .settings(catsSettings)
  .settings(logSettings)
  .settings(serverSettings)
  .settings(fs2Settings)
  .settings(configMuSettings)
  .settings(prometheusSettings)
  .settings(htt4sSettings)

lazy val fs2client: Project = project
  .in(file("fs2-client"))
  .settings(catsSettings)
  .settings(logSettings)
  .settings(clientNettySettings)
  .settings(fs2Settings)
  .settings(configMuSettings)
  .settings(prometheusSettings)
  .settings(htt4sSettings)
  .dependsOn(fs2server)

lazy val monixServer: Project = project
  .in(file("monix-server"))
  .settings(catsSettings)
  .settings(logSettings)
  .settings(serverSettings)
  .settings(monixSettings)
  .settings(configMuSettings)
  .settings(jodatimeMarshallerSettings)

lazy val monixClient: Project = project
  .in(file("monix-client"))
  .settings(catsSettings)
  .settings(logSettings)
  .settings(clientOkhttpSettings)
  .settings(monixSettings)
  .settings(configMuSettings)
  .settings(jodatimeMarshallerSettings)
  .dependsOn(monixServer)
