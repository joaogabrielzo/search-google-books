package com.zo

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}

import scala.concurrent.Future

class RequestService(apiKey: String)(implicit sys: ActorSystem) {
    
    def apiRequest(title: String, optAuthor: Option[String], optPublisher: Option[String],
                   optSubject: Option[String]
                  ): Future[HttpResponse] = {
        
        val author = optAuthor.getOrElse("")
        val publisher = optPublisher.getOrElse("")
        val subject = optSubject.getOrElse("")
        
        val request =
            HttpRequest(
                uri = s"https://www.googleapis.com/books/v1/volumes?q=$title+inauthor:$author+inpublisher" +
                      s":$publisher+subject:$subject&key=$apiKey")
        
        Http().singleRequest(request)
    }
}