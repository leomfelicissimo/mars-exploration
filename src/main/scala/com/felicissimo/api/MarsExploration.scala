package com.felicissimo.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import _root_.entity.Probe
import com.felicissimo.controller.{ControlCenter, Coordinate}
import common._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object MarsExploration {
  implicit val system = ActorSystem("mars-exploration")
  implicit val marterializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val controlCenter = new ControlCenter

  final case class MoveRequest(probeName: String, sequence: String)
  final case class ProbeRequest(name: String, x: Int, y: Int, direction: String)
  final case class ProbeResponse(name: String, x: Int, y: Int, direction: String)
  implicit val moveRequestFormat = jsonFormat2(MoveRequest)
  implicit val probeRequestFormat = jsonFormat4(ProbeRequest)
  implicit val probeResponseFormat = jsonFormat4(ProbeResponse)

  def main(args: Array[String]): Unit = {
    val route: Route =
      path("probes") {
        concat(
          get {
            complete(controlCenter.getProbes().map(toProbeResponse))
          },
          post {
            entity(as[ProbeRequest]) { request =>
              complete(toProbeResponse(controlCenter.sendProbe(fromProbeRequest(request))))
            }
          },
          put {
            entity(as[MoveRequest]) { request =>
              val maybeProbe = controlCenter.sendCommand(request.sequence, request.probeName)
              complete(maybeProbe match {
                case Some(maybeProbe) => toProbeResponse(maybeProbe)
                case None => StatusCodes.NotFound
              })
            }
          }
        )
      }

    val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8080)
    println("Server online at http://localhost:8080/")

    sys.addShutdownHook {
      bindingFuture
        .flatMap(_.unbind())
        .onComplete(_ => system.terminate())
    }

    Await.ready(bindingFuture, Duration.Inf)
  }

  def toProbeResponse(probe: Probe) = {
    val name = probe.name
    val x = probe.coordinate.x
    val y = probe.coordinate.y
    val direction = probe.coordinate.direction.toString()
    ProbeResponse(name, x, y, direction)
  }

  def fromProbeRequest(request: ProbeRequest): Probe = {
    val direction = request.direction match {
      case "North" => North
      case "South" => South
      case "East" => East
      case "West" => West
    }

    Probe(request.name, Coordinate(request.x, request.y, direction))
  }
}
