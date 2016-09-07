package org.thedevpiece.finatra.slick.microservices.api

/**
 * @author Gabriel Francisco <gabfssilva@gmail.com>
 */
object Resources {
  case class User(id: Option[Long], username: String, age: Int, occupation: String)
}
