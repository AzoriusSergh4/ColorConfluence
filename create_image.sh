cd Frontend/cc/
docker run -it --rm --name angularInstallation -v ${PWD}:/src -w /src node:14.13.1 /bin/bash -c "npm install -g @angular/cli; npm install; ng add angular-cli-ghpages; ng deploy --repo=https://github.com/AzoriusSergh4/ColorConfluence.git --name='AzoriusSergh4' --email=sergiolidi@gmail.com --base-href=/ColorConfluence/"
echo 'Instalacion de Angular completada'
cd ../../Backend/
docker run -it --rm --name  mavenInstalation -v ${PWD}:/src -w /src maven:3.6.3-jdk-11 mvn clean install -DskipTests
echo 'Instalacion y empaquetado de Maven completado'
cd ../
docker build -t azoriussergh/colorconfluence .
