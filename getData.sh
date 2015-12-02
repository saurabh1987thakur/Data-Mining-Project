#!/bin/bash
touch final.csv
while read p; 
do
	touch f1
	touch cast
	touch writers 
	touch directors

	echo "\"MOV $p\"" >> f1
	python imdb --writers  "$p" > writers
	echo $(head -n 1 writers) \" >> f1
	python imdb --directors "$p" > directors
	echo $(head -n 1 directors) \" >> f1
	echo >> f1
	python imdb --cast "$p" > cast
	echo $(head -n 1 cast) \" >> f1
	echo >> f1
	paste -d, -s f1 >> final.csv

	rm cast
	rm writers
	rm directors
	rm f1
done < movieList.txt
