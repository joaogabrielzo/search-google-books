package com.zo

import akka.actor.ActorSystem
import akka.http.scaladsl.server.{HttpApp, Route}
import com.typesafe.config.ConfigFactory


object ApplicationServer
    extends HttpApp
    with App {
    
    implicit val sys = ActorSystem()
    
    val config = ConfigFactory.load()
    val requestService = new RequestService(config.getString("google.apiKey"))
    
    val routes: Route = new ApiRequestRoute(requestService).apiRequestRoute
    
    startServer("localhost", 9999)
}