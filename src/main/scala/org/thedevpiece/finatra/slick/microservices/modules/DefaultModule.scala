package org.thedevpiece.finatra.slick.microservices.modules

import com.google.inject.{ Provides, Singleton }
import com.twitter.inject.TwitterModule
import slick.driver.{ H2Driver, JdbcProfile }

/**
 * @author Gabriel Francisco <gabfssilva@gmail.com>
 */
object DefaultModule extends TwitterModule {
  @Singleton
  @Provides
  def jdbcDriver(): JdbcProfile = H2Driver
}
