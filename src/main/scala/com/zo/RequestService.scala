package com.zo

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}

import scala.concurrent.Future
import scala.io.Source

class RequestService extends HttpSetup {
    
    def apiRequest(optTitle: Option[String], optAuthor: Option[String], optPublisher: Option[String],
                   optSubject: Option[String]
                  )
    : Future[HttpResponse] = {
        
        val title = optTitle match {case Some(value) => value; case None => ""}
        val author = optAuthor match {case Some(value) => value; case None => ""}
        val publisher = optPublisher match {case Some(value) => value; case None => ""}
        val subject = optSubject match {case Some(value) => value; case None => ""}
        
        Http().singleRequest(HttpRequest(
            uri = s"https://www.googleapis.com/books/v1/volumes?q=intitle:$title+inauthor:$author+inpublisher" +
                  s":$publisher+subject:$subject&key=$apiKey"))
    }
}
