import config.Config
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scenarios.{HealthcheckScenario, RegistrationScenario}


//creates a course, a professor and a student and assigns both to the course

class RegistrationSimulation extends Simulation {

  private val httpConf = http.baseUrl(Config.BASE_URL)

  //simulation executes one or more scenarios
  private val scenarioToExec1 = RegistrationScenario.registrationScenario1

  //run simulation (which executes scenario(s) which executes one or more chains, simplez!)
  setUp(
    scenarioToExec1.inject(
      rampUsers(Config.USERS) //number of parallel users to emilate (see Config)
        during (Config.DURATION) //duration of simulation run (see Config)
    )
  ).protocols(httpConf)
}