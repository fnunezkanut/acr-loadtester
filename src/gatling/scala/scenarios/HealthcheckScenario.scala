package scenarios

import config.Config
import io.gatling.core.Predef._
import io.gatling.http.Predef._

object HealthcheckScenario {

  //a chain is where the meat of the work happens
  val healthcheckChain1 =
    exec(
      http("request: health endpoint")
        .get("/internal/health")
        .headers(Config.commonHeaders)
        .check(
          status.is(200),
          jsonPath("$.status").is("UP")
        )
    )

  //scenario executes 1 or more chains
  val healthcheckScenario1 = scenario("HealthcheckScenario")
    .exec(healthcheckChain1)
}
