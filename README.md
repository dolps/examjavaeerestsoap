Exam in Java Enterprise 2 Rest Api's
Rest,Soap
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
I've had to do some hack fixes in QuizCommand for the gameSoap module, there was some issues combining
Wiremock with soap and client API, thats why some calls may seem a little
redundant and abnormal but i think its all been commented, and for the hystrix call
it kinda looses the point of using a circuit breaker for the soap module, but for gameAPI.
It should be good, it uses its own client from jersey and i've had no problems with that.
I really hope it builds fine on your laptop, its working fine on mine now
after hours of trying back and forth different solutions. otherwise you know which module to ignore,
from the project. I hope you take into consideration the different environments here,
and that yours may run differently than mine, if there were to be an issue with that module.

The application quizimplementation ran earlier on Open shift,
thats why there had to be two profiles in the pom so only the quiz implementation will
be ran. The api is open and accessible but there was issue with cross origin for swagger UI,
i guess it has to do with swagger html pointing to localhost:8080, since
the cors filter didnt have any impact on it