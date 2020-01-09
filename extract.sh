#!/bin/bash

echo "running the extractor"

read -p "enter input file address  " inputFileAddress

java -cp ~/prj/Steganography/build/ Extractor $inputFileAddress
