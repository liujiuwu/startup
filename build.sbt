name := "Startup"

version := "1.0"

organization := "com.startup"

crossScalaVersions := Seq("2.10.0", "2.9.2", "2.9.1-1", "2.9.1")

scalaVersion in ThisBuild := "2.9.2"

scalacOptions ++= Seq("-deprecation", "-unchecked")

scanDirectories in Compile := Nil

//resolvers ++= Seq(
//"snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
//"releases" at "http://oss.sonatype.org/content/repositories/releases",
//"Media4u101 SNAPSHOT Repository" at "http://www.media4u101.se:8081/nexus/content/repositories/snapshots/",
//"Media4u101 Repository" at "http://www.media4u101.se:8081/nexus/content/repositories/releases/"
//)

//"releases" at "https://oss.sonatype.org/content/repositories/public"

//resolvers ++= Seq(
//"small nexus" at "http://58.61.152.91:10004/nexus/content/groups/public/",
//"public" at "https://oss.sonatype.org/content/repositories/public",
//"releases" at "http://oss.sonatype.org/content/repositories/releases"
//)

resolvers ++= Seq(
	"small nexus" at "http://58.61.152.91:10004/nexus/content/groups/public/",
	"oss public" at "http://oss.sonatype.org/content/repositories/public",
	"oss releases" at "http://oss.sonatype.org/content/repositories/releases",
	"Scala Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
    "Scala" at "https://oss.sonatype.org/content/groups/scala-tools/"
)



seq(com.github.siasia.WebPlugin.webSettings :_*)

port in container.Configuration := 9000

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

seq(lessSettings:_*)


(sourceDirectory in (Compile, LessKeys.less)) <<= (sourceDirectory in Compile)(_ / "webapp" / "css")

(resourceManaged in (Compile, LessKeys.less)) <<= (sourceDirectory in Compile)(_ / "webapp" / "css")

//(LessKeys.mini in (Compile, LessKeys.less)) := true

(LessKeys.filter in (Compile, LessKeys.less)) := "bootstrap.less"


EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

EclipseKeys.executionEnvironment := Some(EclipseExecutionEnvironment.JavaSE16)

EclipseKeys.withSource := true

libraryDependencies ++= {
  val liftVersion = "2.5-M4"
  Seq(
    "net.liftweb"       %% "lift-webkit"        % liftVersion         % "compile->default"  withSources(),
    "net.liftweb"       %% "lift-mapper"        % liftVersion        % "compile->default"  withSources(),
    "net.liftmodules"   %% "lift-jquery-module" % (liftVersion + "-2.1"),
    "net.liftmodules"   %% "widgets" % (liftVersion + "-1.2"),
    "net.liftmodules"   %% "fobo" % (liftVersion + "-0.8.0-SNAPSHOT")  withJavadoc() withSources(),
    "mysql" 	          %  "mysql-connector-java"   % "5.1.21",
    "org.eclipse.jetty" % "jetty-webapp"        % "8.1.7.v20120910"  % "container,test",
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,test" artifacts Artifact("javax.servlet", "jar", "jar"),
    "ch.qos.logback"    % "logback-classic"     % "1.0.6"
  )
}



seq(cloudBeesSettings :_*)

//seq(lessSettings:_*)

CloudBees.apiKey := Some("DCCBAA182C43FAA4")

CloudBees.apiSecret := Some("XUAVAOFUQSQGH5XPEUZPB+/SL4XAY0BI8/IP1E2Q4PA=")

CloudBees.username := Some("liujiuwu")

CloudBees.applicationId := Some("liujiuwu/startup")


