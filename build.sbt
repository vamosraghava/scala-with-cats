import scala.sys.process._

ThisBuild / name               := "scala-with-cats"
ThisBuild / organization       := "com.scalawithcats"
ThisBuild / version            := "0.0.1"

ThisBuild / scalaVersion       := "2.13.1"

ThisBuild / useSuperShell      := false
Global    / logLevel           := Level.Warn


enablePlugins(MdocPlugin)
mdocIn  := sourceDirectory.value / "pages"
mdocOut := target.value          / "pages"

val catsVersion  = "2.1.0"

libraryDependencies ++= Seq("org.typelevel" %% "cats-core" % catsVersion)

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)

mdocVariables := Map(
  "SCALA_VERSION" -> scalaVersion.value,
  "CATS_VERSION" -> catsVersion
)


lazy val pdf  = taskKey[Unit]("Build the PDF version of the book")
lazy val html = taskKey[Unit]("Build the HTML version of the book")
lazy val epub = taskKey[Unit]("Build the ePub version of the book")
lazy val json = taskKey[Unit]("Build the Pandoc JSON AST of the book")
lazy val all  = taskKey[Unit]("Build all versions of the book")

pdf  := { mdoc.toTask("").value ; "grunt pdf"  ! }
html := { mdoc.toTask("").value ; "grunt html" ! }
epub := { mdoc.toTask("").value ; "grunt epub" ! }
json := { mdoc.toTask("").value ; "grunt json" ! }
all  := { pdf.value ; html.value ; epub.value ; json.value }
