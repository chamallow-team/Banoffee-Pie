#!/bin/bash

find . -name "*.class" -type f -delete
javac BanoffeePie/Driver.java

java BanoffeePie/Driver
