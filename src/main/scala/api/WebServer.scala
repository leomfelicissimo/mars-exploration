package api

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{as, complete, concat, entity, get, onComplete, onSuccess, path, pathPrefix, post}
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol.{jsonFormat1, jsonFormat2}

import scala.concurrent.Future
import scala.io.StdIn

object WebServer {
  implicit val system = ActorSystem("mars-exploration")
  implicit val marterializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  var orders: List[Item] = Nil

  final case class Item(name: String, id: Long)
  final case class Order(items: List[Item])

  implicit val itemFormat = jsonFormat2(Item)
  implicit val orderFormat = jsonFormat1(Order)

  def fetchItem(itemId: Long): Future[Option[Item]] = Future {
    orders.find(o => o.id == itemId)
  }

  def saveOrder(order: Order): Future[Done] = {
    orders = order match {
      case Order(items) => items ::: orders
      case _            => orders
    }

    Future { Done }
  }

  def main(args: Array[String]) {
    val route =
      concat(
        get {
          pathPrefix("item" / LongNumber) { id =>
            val maybeItem: Future[Option[Item]] = fetchItem(id)
            onSuccess(maybeItem) {
              case Some(item) => complete(item)
              case None       => complete(StatusCodes.NotFound)
            }
          }
        },
        post {
          path("create-order") {
            entity(as[Order]) { order =>
              val saved: Future[Done] = saveOrder(order)
              onComplete(saved) { done =>
                complete("order created")
              }
            }
          }
        }
      )

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println("Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}
