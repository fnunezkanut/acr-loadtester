package scenarios

import config.Config
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random

object RegistrationScenario {

  def randomText(textLength: Int = 8, alphabet: List[Char] = ('0' to '9').toList) = {
    (1 to textLength).toList.map { charPos =>
      val randomIndex = (Math.random() * alphabet.length).floor.toInt
      alphabet(randomIndex)
    }.mkString("")
  }

  val randomFeeder = Iterator.continually(
    Map(
      "courseName" -> (Random.alphanumeric.take(15).mkString),
      "courseCode" -> (Random.alphanumeric.take(10).mkString),
      "studentFirstName" -> (Random.alphanumeric.take(10).mkString),
      "studentLastName" -> (Random.alphanumeric.take(10).mkString),
      "studentCode" -> randomText( textLength = 8 ),
      "professorFirstName" -> (Random.alphanumeric.take(10).mkString),
      "professorLastName" -> (Random.alphanumeric.take(10).mkString),
      "professorCode" -> randomText( textLength = 7 ),
    )
  )

  //a chain is where the meat of the work happens
  val registrationChain = feed(randomFeeder)
    .exec(
      http("request: create course")
        .post("/v1/courses")
        .headers(Config.commonHeaders)
        .headers(
          Map(
            "Content-Type" -> "application/json"
          )
        )
        .body(StringBody(session =>
          s"""{"name":"${session("courseName").as[String]}","code":"${session("courseCode").as[String]}"}""")
        ).asJson
        .check(
          status.is(201),
          jsonPath("$.uid").saveAs("courseUid")
        )
    )
    .exec(
      http("request: view created course")
        .get(session =>
          s"/v1/courses/${session("courseUid").as[String]}"
        )
        .headers(Config.commonHeaders)
        .headers(
          Map(
            "Content-Type" -> "application/json"
          )
        )
        .check(
          status.is(200)
        )
    )
    .exec(
      http("request: create professor")
        .post("/v1/professors")
        .headers(Config.commonHeaders)
        .headers(
          Map(
            "Content-Type" -> "application/json"
          )
        )
        .body(StringBody(session =>
          s"""{"firstName":"${session("professorFirstName").as[String]}","lastName":"${session("professorLastName").as[String]}","code":"${session("professorCode").as[String]}"}""")
        ).asJson
        .check(
          status.is(201),
          jsonPath("$.uid").saveAs("professorUid")
        )
    )
    .exec(
      http("request: view created professor")
        .get(session =>
          s"/v1/professors/${session("professorUid").as[String]}"
        )
        .headers(Config.commonHeaders)
        .headers(
          Map(
            "Content-Type" -> "application/json"
          )
        )
        .check(
          status.is(200)
        )
    )
    .exec(
      http("request: assign professor to course")
        .post(session =>
          s"/v1/courses/${session("courseUid").as[String]}/professors/${session("professorUid").as[String]}"
        )
        .headers(Config.commonHeaders)
        .check(
          status.is(201)
        )
    )
    .exec(
      http("request: create student")
        .post("/v1/students")
        .headers(Config.commonHeaders)
        .headers(
          Map(
            "Content-Type" -> "application/json"
          )
        )
        .body(StringBody(session =>
          s"""{"firstName":"${session("studentFirstName").as[String]}","lastName":"${session("studentLastName").as[String]}","code":"${session("studentCode").as[String]}"}""")
        ).asJson
        .check(
          status.is(201),
          jsonPath("$.uid").saveAs("studentUid")
        )
    )
    .exec(
      http("request: view created student")
        .get(session =>
          s"/v1/students/${session("studentUid").as[String]}"
        )
        .headers(Config.commonHeaders)
        .headers(
          Map(
            "Content-Type" -> "application/json"
          )
        )
        .check(
          status.is(200)
        )
    )
    .exec(
      http("request: register student to course")
        .post(session =>
          s"/v1/courses/${session("courseUid").as[String]}/students/${session("studentUid").as[String]}"
        )
        .headers(Config.commonHeaders)
        .check(
          status.is(201)
        )
    )

  //scenario executes 1 or more chains
  val registrationScenario1 = scenario("RegistrationScenario")
    .exec(registrationChain)
}
