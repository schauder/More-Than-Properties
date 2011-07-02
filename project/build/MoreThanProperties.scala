import sbt._

class MoreThanProperties(info: ProjectInfo) extends DefaultProject(info)
{
  val scalatest = "org.scalatest" %% "scalatest" % "1.6.1"
  val junit = "junit" % "junit" % "4.8.2"
  //val junitInterface = "com.novocode" % "junit-interface" % "0.6" % "test->default"
  //val grizzled = "org.clapper" %% "grizzled-slf4j" % "0.3.2"
}
