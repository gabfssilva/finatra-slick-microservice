package org.thedevpiece.finatra.slick.microservices.api

import com.google.inject.{Inject, Singleton}
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import org.thedevpiece.finatra.slick.microservices.utils.FutureUtils._
import org.thedevpiece.finatra.slick.microservices.api.Resources.User
import org.thedevpiece.finatra.slick.microservices.domain.UserRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
@Singleton
class UserEndpoint @Inject()(repository: UserRepository) extends Controller {
  get("/users/:id") { request: Request =>
    repository
      .findById(request.getLongParam("id"))
      .asTwitterFuture
      .map({
        case Nil => response.notFound
        case user +: Nil => response.ok((User.apply _).tupled(user))
      })
  }

  post("/users") { user: User =>
    repository
      .create(User.unapply(user).get)
      .map({
        case None => response.internalServerError("Error while creating an user")
        case Some(id) => response.created.location("/users/" + id)
      })
      .asTwitterFuture
  }
}
