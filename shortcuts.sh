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

arithmeticsArtifactId=arithmetics
stringhelperArtifactId=stringhelper

function package_all() {
  if [[ -d $basedir/$arithmeticsArtifactId ]]; then
    echo "Packaging subproject $arithmeticsArtifactId"
    cd $basedir/$arithmeticsArtifactId && mvn package
  fi
  if [[ ! -d $basedir/$stringhelperArtifactId ]]; then
    echo "Packaging subproject $stringhelperArtifactId"
    cd $basedir/$stringhelperArtifactId && mvn package
  fi
}

function exec_arithmetics() {
  if [[ -d $basedir/$arithmeticsArtifactId ]]; then
    echo "Executing subproject $arithmeticsArtifactId"
    cd $basedir/$arithmeticsArtifactId && mvn exec:java -Dexec.mainClass="$groupId.App" #-Dexec.args="argument1" ...
  fi
}

function exec_stringhelper() {
  if [[ ! -d $basedir/$stringhelperArtifactId ]]; then
    echo "Executing subproject $stringhelperArtifactId"
    cd $basedir/$stringhelperArtifactId && mvn exec:java -Dexec.mainClass="$groupId.App" #-Dexec.args="argument1" ...
  fi
}

function create_subprojects () {
  if [[ ! -d $basedir/$arithmeticsArtifactId ]]; then
    echo "Creating subproject $arithmeticsArtifactId"
    cd $basedir && mvn -X archetype:generate -DgroupId=$groupId -DartifactId=$arithmeticsArtifactId -DarchetypeArtifactId=$archetypeArtifactId -DarchetypeVersion=$archetypeVersion -DinteractiveMode=false
  fi
  if [[ ! -d $basedir/$stringhelperArtifactId ]]; then
    echo "Creating subproject $stringhelperArtifactId"
    cd $basedir && mvn -X archetype:generate -DgroupId=$groupId -DartifactId=$stringhelperArtifactId -DarchetypeArtifactId=$archetypeArtifactId -DarchetypeVersion=$archetypeVersion -DinteractiveMode=false
  fi
}

operationCode=$1
$operationCode
