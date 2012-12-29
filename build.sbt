name := "Startup"

version := "1.0"

organization := "net.liftweb"

scalaVersion := "2.9.2"

resolvers ++= Seq("snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
                "releases"        at "http://oss.sonatype.org/content/repositories/releases"
                )

seq(com.github.siasia.WebPlugin.webSettings :_*)

port in container.Configuration := 9000

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

seq(lessSettings:_*)

scalacOptions ++= Seq("-deprecation", "-unchecked")

(resourceManaged in (Compile, LessKeys.less))  <<= (sourceDirectory in Compile)(_ / "webapp/css/")

(LessKeys.filter in (Compile, LessKeys.less)) := "bootstrap.less"

libraryDependencies ++= {
  val liftVersion = "2.5-M3"
  Seq(
    "net.liftweb"       %% "lift-webkit"        % liftVersion        % "compile",
    "net.liftweb"       %% "lift-mapper"        % liftVersion        % "compile",
    "net.liftmodules"   %% "lift-jquery-module" % (liftVersion + "-2.0"),
    "org.eclipse.jetty" % "jetty-webapp"        % "8.1.7.v20120910"  % "container,test",
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,test" artifacts Artifact("javax.servlet", "jar", "jar"),
    "ch.qos.logback"    % "logback-classic"     % "1.0.6",
    "org.specs2"        %% "specs2"             % "1.12.1"           % "test"
  )
}

