# syntax=docker/dockerfile:1

FROM openjdk:11

ARG NPM_REGISTRY

RUN apt-get update
RUN apt-get install -y curl
RUN apt-get install -y p7zip
RUN apt-get install -y p7zip-full
RUN apt-get install -y unace
RUN apt-get install -y zip
RUN apt-get install -y unzip
RUN apt-get install -y bzip2
RUN apt-get install -y xvfb
    
ARG CHROME_VERSION=96.0.4664.110
ARG CHROMDRIVER_VERSION=96.0.4664.45

# Set up the Chrome PPA
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
RUN echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list

# Update the package list and install chrome
RUN apt-get update -y
RUN apt-get install -y google-chrome-stable

# Set up Chromedriver Environment variables
ENV CHROMEDRIVER_VERSION 2.19
ENV CHROMEDRIVER_DIR /chromedriver
RUN mkdir $CHROMEDRIVER_DIR

# Download and install Chromedriver
RUN wget -q --continue -P $CHROMEDRIVER_DIR "http://chromedriver.storage.googleapis.com/$CHROMEDRIVER_VERSION/chromedriver_linux64.zip"
RUN unzip $CHROMEDRIVER_DIR/chromedriver* -d $CHROMEDRIVER_DIR

# Put Chromedriver into the PATH
ENV PATH $CHROMEDRIVER_DIR:$PATH
	
WORKDIR /app

COPY build/* /app

CMD ["java", "-jar", "crawler-k-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]