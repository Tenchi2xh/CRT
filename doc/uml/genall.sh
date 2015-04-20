#!/bin/sh

for f in *.puml
do
    echo "Processing file $f..."
    sh genuml.sh $f
done
