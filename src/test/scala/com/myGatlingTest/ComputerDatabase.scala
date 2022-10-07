package com.myGatlingTest

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ComputerDatabase extends Simulation {

	val httpProtocol = http
		.baseUrl("https://computer-database.gatling.io")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36")

	val scn = scenario("ComputerDatabase")
		.exec(http("NewComputerPage")
			.get("/computers/new"))
		.pause(1)
		.exec(http("CreateNewComputer")
			.post("/computers")
			.formParam("name", "Computer03")
			.formParam("introduced", "2021-10-01")
			.formParam("discontinued", "2022-01-01")
			.formParam("company", "4"))
		.pause(1)
		.exec(http("FilterNewComputer")
			.get("/computers?f=Computer03"))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}