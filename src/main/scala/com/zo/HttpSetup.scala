package com.zo

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route

import scala.concurrent.ExecutionContextExecutor
import scala.io.Source

trait HttpSetup {
    
    implicit val system: ActorSystem = ActorSystem()
    implicit val ec: ExecutionContextExecutor = system.dispatcher
    
    val requestService = new RequestService
    val requestRoute: Route = new ApiRequestRoute(requestService).apiRequestRoute
    
    val googleApiKeyFile = Source.fromFile("src/main/resources/google_api_key.txt")
    val apiKey = googleApiKeyFile.mkString.split(" ")(1).trim
}
