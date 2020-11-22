package config

object Config {
  val BASE_URL: String = sys.env.getOrElse("ACR_URL", sys.props.getOrElse("sim.acr.url", "http://127.0.0.1:8080"))
  val USERS: Int = sys.env.getOrElse("ACR_USERS", sys.props.getOrElse("sim.acr.users", "1")).toInt //number concurrent users
  val DURATION: Int = sys.env.getOrElse("ACR_DURATION", sys.props.getOrElse("sim.acr.duration", "60")).toInt //duration of simulation

  val commonHeaders = Map( //send this header with every request
    "user-agent" -> "acr-loadtester"
  )
}
