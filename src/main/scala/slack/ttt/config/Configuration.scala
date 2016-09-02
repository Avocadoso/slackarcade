package slack.ttt.config

/**
  * Created by Zhengyi on 9/1/2016.
  */
import scala.util.Properties

import com.typesafe.config.ConfigFactory

import util.Try

trait Configuration {
  val config = ConfigFactory.load()
  lazy val servicePort = Integer.parseInt(Properties.envOrElse("PORT", "80"))
  lazy val serviceHost = Try(config.getString("service.host")).getOrElse("localhost")
}