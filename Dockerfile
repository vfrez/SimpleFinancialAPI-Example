FROM openjdk:21-jdk

# Set the working directory to /app
WORKDIR /app

ARG JAR_FILE=target/*.jar

# Copy the source code to the container - <fonte> <destino>
COPY ${JAR_FILE} ./application.jar

#Expondo porta para acessar dados da JVM remotamente
EXPOSE 9010

#Limitande uso da JVM a 75% da RAM do host
#Configurando o GC para ser mais eficiente
#Liberando porta para acessar dados da JVM remotamente
ENTRYPOINT ["java", \
"-Dcom.sun.management.jmxremote", \
"-Dcom.sun.management.jmxremote.port=9010", \
"-Dcom.sun.management.jmxremote.local.only=false", \
"-Dcom.sun.management.jmxremote.authenticate=false", \
"-Dcom.sun.management.jmxremote.ssl=false", \
"-XX:MaxRAMPercentage=75", \
"-XX:+UseG1GC", \
"-XX:+ParallelRefProcEnabled", \
"-XX:+AlwaysPreTouch", \
"-XX:+ExitOnOutOfMemoryError", \
"-Xms1024m", \
"-Xms1024m", \
"-jar", \
"./application.jar"]
