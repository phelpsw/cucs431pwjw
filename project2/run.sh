#!/bin/sh


JAVA_HOME=/usr
PROJ_HOME=nb_aws_owl

$JAVA_HOME/bin/java -classpath "\
$PROJ_HOME/lib/aterm-java-1.6.jar:\
$PROJ_HOME/lib/commons-logging-1.1.1.jar:\
$PROJ_HOME/lib/jena.jar:\
$PROJ_HOME/lib/log4j-1.2.12.jar:\
$PROJ_HOME/lib/pellet.jar:\
$PROJ_HOME/lib/arq.jar:\
$PROJ_HOME/lib/xercesImpl.jar:\
$PROJ_HOME/lib/iri.jar:\
$PROJ_HOME/lib/icu4j_3_4.jar:\
$PROJ_HOME/lib/relaxngDatatype.jar:\
$PROJ_HOME/lib/xsdlib.jar:\
$PROJ_HOME/lib/concurrent.jar:\
$PROJ_HOME/lib/xml-apis.jar" -jar "$PROJ_HOME/dist/nb_aws_owl.jar"
  