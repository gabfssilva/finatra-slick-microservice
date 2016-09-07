package org.thedevpiece.finatra.slick.microservices

import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import org.thedevpiece.finatra.slick.microservices.api.UserEndpoint
import org.thedevpiece.finatra.slick.microservices.modules.DefaultModule

/**
 * @author Gabriel Francisco <gabfssilva@gmail.com>
 */
class ApplicationHttpServer extends HttpServer {
  override val defaultFinatraHttpPort: String = ":8080"
  override val modules = Seq(DefaultModule)

  override protected def configureHttp(router: HttpRouter): Unit = {
    router
      .add[UserEndpoint]
  }
}

object ApplicationHttpServerMain extends ApplicationHttpServer {
}
