openshift-version
====================

#gameAPI
 * swagger at http://localhost:9000/game/
 * include the game.yml by adding server game.yml see below
 * from root of gameAPI java -jar target/game.jar server game.yml

#quizAPI
 * has dtos and defines the rest resources

#quizIMPL
 *implementation of the quizAPI

#gameSoap
 *soapversion of the gameAPI

#gameCommands
 *hystrix commands + dto's of the gameAPI

#entites
 *contains the ejbs repositories and entites

#report
 *generates test result based on the modules

# Note
I've had to do some hack fixes in the gameSoap module, there was some issues combining
Wiremock with soap and client API, thats why some calls may seem a little
redundant and abnormal but i think its all been commented.
I really hope it builds fine on your laptop, its working fine on mine now
after hours of trying. otherwise you know which module to ignore.

The application quizimplementation runs on Open shift and can bee seen
at