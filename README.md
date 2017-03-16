# auth-service
API for User management and security (using OAuth2 & JWT).
The main idea is to provide open source services/"microservices" instead of libraries.

[![Build Status](https://travis-ci.org/open-fun/auth-service.svg?branch=master)](https://travis-ci.org/open-fun/auth-service)

## A bit of history (why this raised?)
I saw a lot of "bicycles" implemented for many of clients and companies.
And often their implementation is far away from the best one.
Actually I don't claim that this one is. I just want to have a relatively stable service that have to boost project.
An open API will allow to use this one into existing system as well to be used into new ones.
The service oriented approach allow to start up fast a new project, and just switch or improve it latter one (and to not care about implementation).

If you are not sure if this one fits your project, you can just start using it and then switch to your own implementation just in case if it will be needed.
However I see more value to be improved this project rather than copy it and improve on your end, because in the future it will have to boost your delivery.

## Issues with framework providing model
During learning of spring-boot I had to deal with next issues:
* Most of the examples are using some particular feature (and when you have to combine different features you can deal with config issues)
* I don't saw real world usage of spring security without database storage (while most of examples uses inMemory access)
* The same code and configuration has to be done between different projects and companies.
* Most of the OAuth examples are implemented just partially/or with in memory/hardcode storage.

## Output of service oriented project
Definitely Docker image. As today industry standard (considering implementation for most of the Cloud providers).
That also will allow to combine it with different technology stacks/langs into your company.


## Docs for API
Check `.feature` files under `./src/specs/resources/features/` I try to have them as most complete as possible (or reasonable).

## Building
Dependencises:
* JDK8
* Docker
* Docker-compose

run from root of the project: `./mvnw clean install`
Note: First time it has to run a long time as docker images are downloaded. After that it takes about one-two minutes to do full build with tests and docker generation.
To run service do `docker-compse up` form project folder.
The service is accesible at localhost:8082 (check docker-compose.yml configs).

## To use latest docker image
`docker pull openfun/auth-service`
service is served at port `80`

To use it link a database. Check as example how is done in docker-compose.yml
Other details see there: https://hub.docker.com/r/openfun/auth-service/

## License type
Whatever license type you need just ask to be updated.
Just one thing to keep in mind that the license type has to be open for other people too.

## Contributors
Has to be done via pull-request and make sure that all tests are running (as well consider to add your own tests).

## Certification key

The key was generated by using next command:
`keytool -genkeypair -alias testkey -keyalg RSA -dname "CN=jwt,OU=jtw,O=jtw,L=zurich,S=zurich,C=CH" -keypass secret -keystore sample.jks -storepass secret`

For security reasons is better to generate your own key file for your environment.


To export public key use:
`keytool -list -rfc --keystore sample.jks | openssl x509 -inform pem -pubkey`
Copy public key part only into file: `public.cert`
