#!/usr/bin/env bash
set -e

if ! command -v java > /dev/null 2>&1; then
  echo "Java not installed. Install JDK 22.0.1 or newer."
  exit 1
fi

if ! command -v javac > /dev/null 2>&1; then
  echo "Javac not found. Install JDK 22.0.1 or newer"
  exit 1
fi

cd src || exit 1
echo "Compiling..."
javac *.java
echo "Running."
java Main
