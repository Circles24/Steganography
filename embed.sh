#!/bin/bash


rm ~/prj/Steganography/build/*

echo "building the program"

javac -d ~/prj/Steganography/build/ ~/prj/Steganography/src/*.java 2> err.log

if [ $? -eq 0 ]
then

	echo;echo "running the embedder"

	read -p "enter vessel address  " vesselAddress
	read -p "enter secret file address  " secretFileAddress 	
	read -p "enter output image address  " outputImageAddress

	java -cp ~/prj/Steganography/build Embedder $vesselAddress $secretFileAddress $outputImageAddress

else

	echo;echo "compilation failed";echo;
	echo "****************************************"

fi	
