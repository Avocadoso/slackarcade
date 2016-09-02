package slack.ttt

import akka.actor.ActorSystem
import spray.routing.SimpleRoutingApp
import spray.httpx.Json4sSupport
import org.json4s.Formats
import org.json4s.JsonAST.JObject
import org.json4s.DefaultFormats
import slack.ttt.config.Configuration


object Main extends App with SimpleRoutingApp with Configuration with Json4sSupport {

  implicit var actorSystem = ActorSystem()

  // globally override the default format to respond with Json
  implicit def json4sFormats: Formats = DefaultFormats

  var fragments = Fragment.fragments

  startServer(interface = serviceHost, port = servicePort) {
    get {
      path("hello") {
        complete {
          "Hello World!"
        }
      }
    } ~
      get {
        path("fragments") {
          complete {
            fragments
          }
        }
      } ~
      get {
        path("fragment" / IntNumber) { index =>
          complete {
            fragments(index)
          }
        }
      } ~
      post {
        path("fragment") {
          entity(as[JObject]) { fragmentObj =>
            val fragment = fragmentObj.extract[MineralFragment]
            fragments = fragment :: fragments
            complete {
              "OK"
            }
          }
        }
      }
  }
}