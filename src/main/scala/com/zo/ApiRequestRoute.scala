package com.zo

import scala.concurrent.{ExecutionContext, Future}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server._

import scala.util.{Failure, Success}

class ApiRequestRoute(request: RequestService)(implicit ec: ExecutionContext) extends HttpSetup {
    
    val apiRequestRoute: Route =
        (path("book" / "search") & get) {
            parameter("title".?, "author".?, "publisher".?, "subject".?) { (title, author, publisher, subject) =>
                lazy val requestFuture: Future[HttpResponse] = request.apiRequest(title, author, publisher, subject)
                
                onComplete(requestFuture) {
                    case Success(response) =>
                        response.discardEntityBytes()
                        complete(response)
                    case Failure(ex)       =>
                        complete(s"Request failed with: $ex")
                    
                }
                
            }
        }
    
}
