# ACR - Academic Course Registration load tester

Purpose of this project is to show how to loadtest the demo project at https://github.com/fnunezkanut/acr
using Gatling+Scala

### Requirements
* JDK 1.8
* Scala
* Kotlin
* IntelliJ

### Local development of Simulations

* Create a <SimulationName>Simulation.scala class in src/gatling/scala/

* Running a single simulation
* ``./gradlew clean gatlingRun-<SIMULATION_CLASSNAME_HERE>``
* eg: ```./gradlew clean gatlingRun-HealthcheckSimulation```


Running integration simulation with custom properties
* ``./gradlew clean gatlingRun-HealthcheckSimulation -Penv=dev``
where `dev.properties` should exist in `propfiles/` folder. 
Check `propfiles/sample.properties_` for examples and more documentation

If you want more details on what the simulation is doing consider developing only with 1 user as input
in build.gradle edit gatling.logLevel setting from WARN to TRACE

### Viewing load test reports

After each run a html report is generated in /build/reports/gatling/ directory

There is also a simulation.log containing raw simulation data, which could be parsed for further integration...