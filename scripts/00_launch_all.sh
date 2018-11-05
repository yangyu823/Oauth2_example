#!/usr/bin/env bash
echo This will take a few seconds
java -jar ../secureServer/target/secureServer-0.0.1-SNAPSHOT.jar >& ../logs/secureServer.log &
sleep 2 ; echo -n .
java -jar ../secureUi/target/secureUi-0.0.1-SNAPSHOT.jar >& ../logs/secureUi.log &
sleep 2 ; echo -n .
echo ""
ps | grep java | grep jar
echo ""
echo done now try http://localhost:8080/reports to get Token and http://localhost:9002/services/tolldata to pass token
