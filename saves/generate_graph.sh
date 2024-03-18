#!/bin/bash

# Check if a file as been passed as argument
if [ -z "$1" ]; then
    echo "No graphml file passed as argument"
    exit 1
fi

# Check if the file exists
if [ ! -f "$1" ]; then
    echo "File '$1' does not exist"
    exit 1
fi

graphml2gv "$1" | dot -Tpng -o out.png