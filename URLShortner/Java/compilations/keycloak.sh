#! /bin/bash

if [ -d ~/Documents/MyProjects/URLShortner/Java/keycloak-data ] 
then
   echo "Data Directory Already Exist Deleting it"
else
mkdir ~/Documents/MyProjects/URLShortner/Java/keycloak-data
fi


podman run --rm -it -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -v ~/Documents/MyProjects/URLShortner/Java/keycloak-data/:/opt/keycloak/data/ quay.io/keycloak/keycloak:17.0.1 start-dev
