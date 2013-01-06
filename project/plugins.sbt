resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "sonatype.repo" at "https://oss.sonatype.org/content/groups/public"

//resolvers += "sbt-plugin-releases-" at "http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases/"


libraryDependencies <+= sbtVersion(v => v match {
  case "0.11.0" => "com.github.siasia" %% "xsbt-web-plugin" % "0.11.0-0.2.8"
  case "0.11.1" => "com.github.siasia" %% "xsbt-web-plugin" % "0.11.1-0.2.10"
  case "0.11.2" => "com.github.siasia" %% "xsbt-web-plugin" % "0.11.2-0.2.10"
  case "0.11.3" => "com.github.siasia" %% "xsbt-web-plugin" % "0.11.3-0.2.11.1"
  case "0.12.0" => "com.github.siasia" %% "xsbt-web-plugin" % "0.12.0-0.2.11.1"
  case "0.12.1" => "com.github.siasia" %% "xsbt-web-plugin" % "0.12.0-0.2.11.1"
})

//Uncoment this line to enable the sbt idea plugin
addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.2.0")

//Uncoment this line to enable the sbt eclipse plugin
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.1.0")

//addSbtPlugin("com.untyped" %% "sbt-less" % "0.5")

addSbtPlugin("me.lessis" % "less-sbt" % "0.1.10")

