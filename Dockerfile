FROM adoptopenjdk:8-jre-openj9

RUN apt-get update \ 
    && apt-get install wget \
    && wget https://bintray.com/artifact/download/sbt/debian/sbt-1.2.8.deb \
    && dpkg -i sbt-1.2.8.deb \
    && apt-get update \
    && apt-get install sbt

RUN mkdir /usr/app
ADD . /usr/app
VOLUME /usr/app

WORKDIR /usr/app
RUN sbt compile \
    && sbt assembly \
    && sbt package

EXPOSE 8080

CMD ["java", "-jar", "target/scala-2.13/mars-exploration.jar"]
