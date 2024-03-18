#!/bin/bash

find . -name "*.class" -type f -delete
javac BanoffeePieMorpion/Morpion.java

java BanoffeePieMorpion/Morpion
