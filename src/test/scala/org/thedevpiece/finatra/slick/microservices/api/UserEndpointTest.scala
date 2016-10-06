package org.thedevpiece.finatra.slick.microservices.api

import com.twitter.finagle.http.Status
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest
import org.thedevpiece.finatra.slick.microservices.ApplicationHttpServer
import org.thedevpiece.finatra.slick.microservices.domain.UserRepository
import slick.driver.JdbcProfile

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
class UserEndpointTest extends FeatureTest {
  override val server = new EmbeddedHttpServer(new ApplicationHttpServer)

  override protected def beforeAll(): Unit = {
    val driver = injector.instance[JdbcProfile]
    val repository: UserRepository = injector.instance[UserRepository]

    import driver.api._

    val insertActions = DBIO.seq(
      repository.users.schema.create
    )

    repository.db.run(insertActions)
  }

  "Clients" should {
    "be able to create user resources" in {
      val response = server.httpPost(
        path = "/users",
        postBody =
          """{
            	"username": "Gabriel",
            	"age": 23,
            	"occupation": "Software Engineer"
            }"""
      )

      response.status shouldBe Status.Created
      response.location should not be empty
    }
  }

  "Clients" should {
    "be able to fetch a created user" in {
      val response = server.httpPost(
        path = "/users",
        postBody =
          """{
            	"username": "Gabriel",
            	"age": 23,
            	"occupation": "Software Engineer"
            }""",
        andExpect = Status.Created
      )

      response.location should not be empty

      val location = response.location.get
      val id = location.split("/users/")(1)

      server.httpGet(
        path = location,
        andExpect = Status.Ok,
        withJsonBody = s"""{
              "id": $id,
            	"username": "Gabriel",
            	"age": 23,
            	"occupation": "Software Engineer"
            }"""
      )
    }
  }
}
