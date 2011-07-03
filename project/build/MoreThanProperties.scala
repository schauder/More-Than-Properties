import sbt._

class MoreThanProperties(info: ProjectInfo) extends DefaultProject(info)
{
  val jgoodies = "jgoodies" % "forms" % "1.0.5"
 
  
  val scalatest = "org.scalatest" %% "scalatest" % "1.6.1" % "test"
  val junit = "junit" % "junit" % "4.8.2" % "test"
  
  //val junitInterface = "com.novocode" % "junit-interface" % "0.6" % "test->default"
  //val grizzled = "org.clapper" %% "grizzled-slf4j" % "0.3.2"
}
