#!/usr/bin/env bash

MAIN_CLASS="Calculator.Calc"
JAR_NAME="CalculatorApp.jar"
BASE_DIR="src/main/java"
PACKAGE_DIR="Calculator"
echo "Compilation is starting"

javac "${BASE_DIR}/${PACKAGE_DIR}"/Calc.java

if [ $? -eq 0 ]; then
  echo "Compilation succesful! .class files created."
  echo "Creating the JAR file..."
  # c - create, f - specifica numele arhivei, e - entry pointul adica unde se afla functia main de unde sa inceapa
  # -C change dir
  jar cfe ${JAR_NAME} ${MAIN_CLASS} -C "${BASE_DIR}" "${PACKAGE_DIR}"

  if [ $? -eq 0 ]; then
    echo "The exec file has been created."
    echo "Run: \" java -jar ${JAR_NAME}\""
  else
    echo "Error creating the JAR file!"
    exit 1
  fi
else
  echo "Compilation error!"
  exit 1
fi
