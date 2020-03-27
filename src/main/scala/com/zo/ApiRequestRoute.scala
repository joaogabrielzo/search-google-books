package com.zo

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import com.typesafe.config.ConfigFactory

import scala.concurrent.Future
import scala.util.{Failure, Success}

class ApiRequestRoute(request: RequestService) {
    
    val config = ConfigFactory.load()
    
    val apiRequestRoute: Route =
        (path("books" / "search") & get) {
            parameter("title", "author".?, "publisher".?, "subject".?) { (title, author, publisher, subject) =>
                val requestFuture: Future[HttpResponse] = request.apiRequest(title, author, publisher, subject)
                
                onComplete(requestFuture) {
                    case Success(response) =>
                        complete(response)
                    case Failure(ex)       =>
                        complete(s"Request failed with: $ex")
                }
            }
        } ~
        pathEndOrSingleSlash {
            getFromFile(config.getString("html.path"))
        }
}