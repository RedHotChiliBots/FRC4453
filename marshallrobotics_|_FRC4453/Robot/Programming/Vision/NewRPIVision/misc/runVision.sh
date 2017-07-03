#! /bin/bash
export JAVA_HOME=/usr/lib/jvm/jdk-8-oracle-arm32-vfp-hflt
JVMARGS="-Djava.library.path=/home/pi/Vision/natives/"
$JAVA_HOME/bin/java $JVMARGS -jar /home/pi/Vision/Vision.jar
