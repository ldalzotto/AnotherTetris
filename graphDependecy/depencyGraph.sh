#!/bin/bash

set -x

currentpath=`realpath ./`
rawDependecyFile="test2.txt"
outputDirectory=${currentpath}/out/plantuml

`./gradlew jar`

rù ${outputDirectory}
rm ${rawDependecyFile}
cd ../
echo `./gradlew desktop:dependencies --configuration compile` >> ${currentpath}/${rawDependecyFile}

cd ${currentpath}

`java -jar ./build/libs/graphDependecy-1.0-SNAPSHOT.jar --fileInput=${rawDependecyFile} --outputDir=${outputDirectory}`
`java -jar plantuml.jar ./out/plantuml/`
