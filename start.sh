#!/bin/sh

LANG=ru_RU.UTF8

java -cp $( echo lib/*.jar . | sed 's/ /:/g') \
     -Dfile.encoding=UTF8 \
     -Dorg.apache.commons.logging.LogFactory=org.apache.commons.logging.impl.Log4jFactory \
     -showversion -server -Xverify:none \
     -Xmx512m -Xms128m -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError \
     net.sf.xfresh.util.Starter buildbeans.xml

echo $!
