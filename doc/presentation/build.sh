#!/bin/sh
pandoc -t beamer $1.md -o $1.pdf -V theme:Antibes -H inc.tex --listings
