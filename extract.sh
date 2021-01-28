#!/bin/bash

echo "running the extractor"

read -p "enter input file address  " inputFileAddress

java -cp ~/dev/temp/Steganography/build/ Extractor $inputFileAddress
