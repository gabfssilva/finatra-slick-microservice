package org.thedevpiece.finatra.slick.microservices.domain

import com.google.inject.{Inject, Singleton}
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

@Singleton
class UserRepository @Inject()(val driver: JdbcProfile) {
  import driver.api._

  /*
    We are assuming we have a table named 'USERS' that has the following columns:
    - IDT_USER (number, auto inc, PK)
    - DES_USERNAME (varchar)
    - NUM_AGE (number)
    - DES_OCCUPATION (varchar)
   */
  class Users(tag: Tag) extends Table[(Option[Long], String, Int, String)](tag, "USERS") {
    def id = column[Long]("IDT_USER", O.AutoInc, O.PrimaryKey)
    def username = column[String]("DES_USERNAME")
    def age = column[Int]("NUM_AGE")
    def occupation = column[String]("DES_OCCUPATION")

    override def * = (id.?, username, age, occupation)
  }

  val users: TableQuery[Users] = TableQuery[Users]
  val db = Database.forConfig("database")

  def findById(id: Long): Future[Seq[(Option[Long], String, Int, String)]] = {
    val query = users.filter(_.id === id).result
    db.run(query)
  }

  def create(user: (Option[Long], String, Int, String)): Future[Option[Long]] = {
    val action: DBIO[Seq[Long]] = (users returning users.map(_.id)) ++= List(user)
    db.run(action).map({
      case Nil => None
      case x +: Nil => Some(x)
    })
  }
}