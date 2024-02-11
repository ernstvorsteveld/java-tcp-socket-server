FROM amazoncorretto:17 AS builder

RUN yum update -y && yum install -y wget && yum install -y unzip

ARG GRADLE_VERSION=7.6.4
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp \
 && unzip -d /opt/gradle /tmp/gradle-${GRADLE_VERSION}-bin.zip \
 && ln -s /opt/gradle/gradle-${GRADLE_VERSION}/bin/gradle /usr/local/bin/gradle

COPY build.gradle .
COPY settings.gradle .
COPY gradle ./gradle
RUN gradle wrapper
COPY src ./src
RUN ./gradlew clean build
RUN ls -ls ./build/libs


FROM amazoncorretto:17 AS runtime
VOLUME /tmp
COPY --from=builder ./build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]