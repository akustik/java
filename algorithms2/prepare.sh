#!/bin/bash

wget -O lib/stdlib.jar http://algs4.cs.princeton.edu/code/stdlib.jar
wget -O lib/algs4.jar wget http://algs4.cs.princeton.edu/code/algs4.jar
wget http://algs4.cs.princeton.edu/linux/checkstyle.zip
unzip checkstyle.zip
wget http://algs4.cs.princeton.edu/linux/findbugs.zip
unzip findbugs.zip
wget -O checkstyle-5.5/checkstyle.xml http://algs4.cs.princeton.edu/linux/checkstyle.xml
wget -O findbugs-2.0.1/findbugs.xml http://algs4.cs.princeton.edu/linux/findbugs.xml
wget -O bin/checkstyle http://algs4.cs.princeton.edu/linux/checkstyle
wget -O bin/findbugs http://algs4.cs.princeton.edu/linux/findbugs
chmod 700 bin/*
rm *.zip

#JAVA

mvn install:install-file -Dfile=lib/stdlib.jar -DgroupId=algs4 \
    -DartifactId=stdlib -Dversion=1.0.0 -Dpackaging=jar
mvn install:install-file -Dfile=lib/algs4.jar -DgroupId=algs4 \
    -DartifactId=algs4 -Dversion=1.0.0 -Dpackaging=jar   
mvn archetype:generate -DgroupId=algs4 -DartifactId=wordnet \
    -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
