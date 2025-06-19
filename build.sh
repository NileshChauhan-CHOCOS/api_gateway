#!/bin/bash

# Extract application name from application.yml
APP_NAME=$(grep '^application.name:' application.yml | awk '{print $2}')

# Pass the app name into the Docker build
docker build --build-arg APP_NAME="$APP_NAME" -t "$APP_NAME-image" .
