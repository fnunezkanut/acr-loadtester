import config.Config
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scenarios.HealthcheckScenario


//does nothing more than call the acr's actuator health endpoint

class HealthcheckSimulation extends Simulation {

  private val httpConf = http.baseUrl(Config.BASE_URL)

  //simulation executes one or more scenarios
  private val scenarioToExec1 = HealthcheckScenario.healthcheckScenario1

  //run simulation (which executes scenario(s) which executes one or more chains, simplez!)
  setUp(
    scenarioToExec1.inject(
      rampUsers(Config.USERS) //number of parallel users to emilate (see Config)
        during (Config.DURATION) //duration of simulation run (see Config)
    )
  ).protocols(httpConf)
}