#!/bin/bash

find . -name "*.class" -type f -delete
javac MorpionMinMax/Morpion.java

java MorpionMinMax/Morpion
