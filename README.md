# GitPublish
Gradle plugin for enabling git repositories to be added as dependencies.

#### What does the plugin do?
Clones and publishes project to a local maven repository.

Set up mavenLocalRepo as a gradle property.

##### Build script:
    gitPublish {
        shouldKeep = false                      //false=deletes project after build, true=keeps the project
        tempDirectoryPath=/home/gdulai/temp     //path to clone the projects, default=root of the project
        repositories = [
                ["repository-name","https://url-to-rep.git"],
                ["repository-name","https://url-to-rep.git","username","password"]
        ]
    }
##### Behaviour:
* In case of maven based project if the plugin does not find a  mave wrapper it uses $MAVEN_HOME or $M2_HOME.
* In case of maven project set the $JAVA_HOME system env.

##### Tips:
* Do not forget to put ssh:// before ssh urls.



