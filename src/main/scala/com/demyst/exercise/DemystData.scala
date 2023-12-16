package com.demyst.exercise
import scala.io.Source
import io.circe.parser._

object DemystData {

  def getIpAddress(apiUrl: String): Either[String, String] = {
    try {
      val response = Source.fromURL(apiUrl).mkString
      val parsedJson = parse(response)
      parsedJson.flatMap(_.hcursor.downField("ip").as[String])
        .left.map(error => s"Error parsing JSON: $error")
    } catch {
      case e: Exception => Left(s"Error fetching data: ${e.getMessage}")
    }
  }

  def main(args: Array[String]): Unit = {
    // Command-line interface
    if (args.length == 0) {
      println("Usage: sbt \"run <API_URL>\"")
    } else {
      val apiUrl = args(0)
      getIpAddress(apiUrl) match {
        case Right(ip) => println(s"Your IP address is: $ip")
        case Left(error) => println(s"Error: $error")
      }
    }
  }
}