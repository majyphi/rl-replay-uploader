#!/bin/bash

CURRENT_DIR="$(pwd)"
TARGET_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

cd $TARGET_DIR

java -jar ./rl-replay-uploader-1.0-jar-with-dependencies.jar ./application.properties

cd $CURRENT_DIR
