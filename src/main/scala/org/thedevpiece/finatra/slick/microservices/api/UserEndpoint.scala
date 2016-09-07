package org.thedevpiece.finatra.slick.microservices.api

import com.google.inject.{ Inject, Singleton }
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
class UserEndpoint @Inject() (userModel: UserRepository) extends Controller {
  get("/users/:id") { request: Request =>
    userModel
      .findById(request.getLongParam("id"))
      .asTwitterFuture
      .map({
        case Nil         => response.notFound
        case user +: Nil => response.ok((User.apply _).tupled(user))
      })
  }

  post("/users") { user: User =>
    userModel
      .create(User.unapply(user).get)
      .map(id => response.created.location("/users/" + id.head))
      .asTwitterFuture
  }
}
