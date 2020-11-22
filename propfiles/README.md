# .properties config files for Gatling load tests explained

You can provide custom configurations for your simulations.
On your local machine you can set them using a .properties file (see _sample.properties) which 
would save one from mucking around with ENV parameters

### Creating and using .properties files

There is a sample file here called `sample.properties_` (underscore so its commited to git) this contains a list of 
java properties this project needs

* Copy it
* Rename it to lets say local.properties
* Edit it to contain the name of your quail egg namespace

### Using .properties file with command line

Run `./gradlew gatlingRun-IntegrationSimulation -Penv=local` assuming `local.properties` 
exists in `$rootDir/propfiles/` folder.