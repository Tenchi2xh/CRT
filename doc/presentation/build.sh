#!/bin/sh
pandoc -t beamer $1.md -o $1.pdf -V theme:Warsaw -H inc.tex --listings
