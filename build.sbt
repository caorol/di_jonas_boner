name := "JonasBoner"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val scaldiV = "0.5.7"
  Seq(
    "org.scaldi"        %% "scaldi" % scaldiV
  )
}