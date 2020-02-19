name := "akka_sample_distributed_workers_frontend"

version := "0.1"
scalaVersion := "2.13.1"

val akkaVersion = "2.6.3"
val cassandraPluginVersion = "0.102"

Global / cancelable := false

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-cluster-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence-query" % akkaVersion,
  "com.typesafe.akka" %% "akka-serialization-jackson" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence-cassandra" % cassandraPluginVersion,
  "com.typesafe.akka" %% "akka-persistence-cassandra-launcher" % cassandraPluginVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "commons-io" % "commons-io" % "2.4" % Test)