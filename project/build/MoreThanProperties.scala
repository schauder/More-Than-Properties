import sbt._

class MoreThanProperties(info: ProjectInfo) extends DefaultProject(info)with AutoCompilerPlugins {
  val scalaToolsSnapshots = "Scala-Tools Maven2 Snapshots Repository" at "http://scala-tools.org/repo-snapshots"
 // val alacs = compilerPlugin("com.github.alacs" %% "alacs" % "0.0.0-SNAPSHOT")
  
  val jgoodies = "jgoodies" % "forms" % "1.0.5"
  val scalatest = "org.scalatest" %% "scalatest" % "1.6.1" % "test"
  val junit = "junit" % "junit" % "4.8.2" % "test"
  val jdepend = "jdepend" % "jdepend" % "2.9.1"

}
