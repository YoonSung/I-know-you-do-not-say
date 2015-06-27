mvn clean install -DskipTests -Pdevelopment
#mvn clean install
mvn exec:java -PintergrationTest -Pdevelopment