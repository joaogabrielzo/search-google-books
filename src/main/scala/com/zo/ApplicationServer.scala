package com.zo

import akka.http.scaladsl.Http

object ApplicationServer extends App with HttpSetup {
    
    Http().bindAndHandle(requestRoute, "localhost", 9999)
}
