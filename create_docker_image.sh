cd Frontend/cc
docker run -it --rm --name angularInstalation -v ${PWD}:/src -w /src node:8.15.1 /bin/bash -c "npm install -g @angular/cli; npm install; npm rebuild; ng build --prod --output-path docs --base-href=/ColorConfluence/"
cp './docs/index.html' './docs/404.html'
echo 'Instalacion de angular completada'
cd ../../Backend
docker run -it --rm --name mavenInstalation -v ${PWD}:/src -w /src maven:3.6-jdk-8 mvn clean install
echo 'Instalacion y empaquetado de maven completada'
cd ..
docker build -t azoriussergh/colorconfluence .