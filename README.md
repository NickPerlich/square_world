compile:
javac -cp "library/org.eclipse.paho.client.mqttv3-1.2.5.jar;src" -d bin src/*.java

run:
java  -cp "library/org.eclipse.paho.client.mqttv3-1.2.5.jar;bin" src.Main