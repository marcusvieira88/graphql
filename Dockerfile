# Dockerfile
FROM mongo
MAINTAINER Marcus Vieira <marcusvinicius.vieira88@gmail.com>
ENV MAVEN_VERSION 3.3.9

RUN apt-get update \
  && apt-get install sudo \
  && sudo apt-get install curl -y

RUN mkdir -p /usr/share/maven \
  && curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz \
    | tar -xzC /usr/share/maven --strip-components=1 \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
VOLUME /root/.m2

#FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
#RUN apt-get update \
#    && apt-get install sudo

#RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv EA312927 \
#  && tee /etc/apt/sources.list.d/mongodb-org-3.2.list \
#  && apt-get update \
#  && apt-get install -y mongodb-org --no-install-recommends \
#  && apt-get clean \
#  && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*
#RUN sudo service mongod start

#RUN sudo apt-get install -y software-properties-common
#RUN sudo add-apt-repository ppa:openjdk-r/ppa
#RUN sudo apt-get update
#RUN sudo apt-get install openjdk-8-jre

#RUN sudo apt-get install -y software-properties-common
#RUN sudo add-apt-repository deb http://ppa.launchpad.net/webupd8team/java/ubuntu xenial main
#RUN sudo apt-get update
#RUN sudo apt-get install -y oracle-java8-installer

RUN apt-get update
RUN sudo apt-get install python-software-properties
RUN sudo add-apt-repository ppa:webupd8team/java
RUN sudo apt-get install oracle-java8-set-default

COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package
ENTRYPOINT ["mvn"]
CMD ["jetty:run"]
EXPOSE 8080
