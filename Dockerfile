# Use Maven with Java 8 as base image
FROM maven:3.8-openjdk-11

# Install wget for downloading Storm
RUN apt-get update && apt-get install -y wget

# Download and install Storm client
RUN wget https://archive.apache.org/dist/storm/apache-storm-2.6.0/apache-storm-2.6.0.tar.gz \
    && tar -xzf apache-storm-2.6.0.tar.gz \
    && mv apache-storm-2.6.0 /usr/local/storm \
    && ln -s /usr/local/storm/bin/storm /usr/local/bin/storm \
    && rm apache-storm-2.6.0.tar.gz

# Set Storm environment variables
ENV STORM_HOME=/usr/local/storm
ENV PATH="${STORM_HOME}/bin:${PATH}"

# Make sure Python is installed
RUN apt-get update && apt-get install -y python3 \
    && ln -s /usr/bin/python3 /usr/bin/python

# Set working directory
WORKDIR /demo

# Keep container running
CMD ["tail", "-f", "/dev/null"] 