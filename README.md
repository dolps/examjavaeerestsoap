openshift-version
====================

# http://tessttt-testingfinal.44fs.preview.openshiftapps.com/
# https://console.preview.openshift.com/console/project/aatestt/edit/builds/teestt

# Java exam 2016 News Application

The project is divided into three main modules
backend, frontend and report.

# backend
The backend module is seperated into annotations, entity, repository and service packages, divided
by their respective layer(purpose), i chose to use the repository abstraction, since it made
it easier to reuse the code and made a better seperation between logic and persistence, actually
inspired by this talk https://www.youtube.com/watch?v=kvjdyqZR2CA

persistence configuration and DI can be found under resources

# frontend
The frontend consists of two main parts the controllers which backs up the JSF, and the jsf
itself. Each page with its respective controller.
Final name and context for deployed war is specified in root pom, if url must be changed from
http://localhost:8080/pg5100_exam

# report
The generated test coverage will be placed here under target/site


mvn clean install, and mvn clean verify can be
executed directly from the root module



# quizgame
# exercisesee2
