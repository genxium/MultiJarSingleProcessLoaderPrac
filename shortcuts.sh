#!/bin/bash

# This script assumes that you are running maven-3.6.3 or above, please refer to http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html for more information.

if [ $# -ne 1 ]; then   
  echo "Usage: $0 <operation code, e.g. \"create_subprojects\" or \"exec_arithmetics\">"   
  exit 1 
fi

basedir=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

groupId=com.mycompany.app
archetypeArtifactId=maven-archetype-quickstart
archetypeVersion=1.4

hybridArtifactId=hybrid
arithmeticsArtifactId=arithmetics
stringhelperArtifactId=stringhelper

function package_wrapped_hybrid() {
  echo "Packaging the wrapped hybrid"
  cd $basedir && mvn package -DskipTests
}

function package_every_individual_subproject() {
  subprojects=( $arithmeticsArtifactId $stringhelperArtifactId )
  for i in "${subprojects[@]}"
  do
    if [[ -d $basedir/$i ]]; then
      echo "Packaging subproject $i"
      cd $basedir/$i && mvn package -DskipTests 
  fi
  done
}

function exec_hybrid() {
  if [[ -d $basedir/$hybridArtifactId ]]; then
    echo "Executing hybrid"
    cd $basedir/$hybridArtifactId && mvn exec:java -Dexec.mainClass="$groupId.App" #-Dexec.args="argument1" ...
  fi
}

function exec_arithmetics() {
  if [[ -d $basedir/$arithmeticsArtifactId ]]; then
    echo "Executing subproject $arithmeticsArtifactId"
    cd $basedir/$arithmeticsArtifactId && mvn exec:java -Dexec.mainClass="$groupId.ArithmeticsApp" #-Dexec.args="argument1" ...
  fi
}

function exec_stringhelper() {
  if [[ -d $basedir/$stringhelperArtifactId ]]; then
    echo "Executing subproject $stringhelperArtifactId"
    cd $basedir/$stringhelperArtifactId && mvn exec:java -Dexec.mainClass="$groupId.StringHelperApp" #-Dexec.args="argument1" ...
  fi
}

function create_subprojects () {
  subprojects=( $hybridArtifactId $arithmeticsArtifactId $stringhelperArtifactId )
  for i in "${subprojects[@]}"
  do
    if [[ ! -d $basedir/$i ]]; then
      echo "Creating subproject $i"
      cd $basedir && mvn -X archetype:generate -DgroupId=$groupId -DartifactId=$i -DarchetypeArtifactId=$archetypeArtifactId -DarchetypeVersion=$archetypeVersion -DinteractiveMode=false
    fi
    cp $basedir/pom-file-templates/$i-pom.xml $basedir/$i/pom.xml 
  done
  cp $basedir/pom-file-templates/root-pom.xml $basedir/pom.xml 
}

operationCode=$1
$operationCode
