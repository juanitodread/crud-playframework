## Simple CRUD with Play Framework and Scala

This project is currently deployed on [Heroku](https://www.heroku.com/home) you can take a look to the app [here](https://crud-lab.herokuapp.com/).

#### How to deploy on Heroku
##### Requirements
* Java 8
* [Activator 1.3.7+](https://www.playframework.com/download)

##### Deploy
1. Clone the repo
2. Go to the root path of the project and run activator to verify that the code compiles
  ```
  $ actvitator run
  ```
3. Go to your Heroku account and create a new application
4. Configure your new application. Go to the *deploy* section and on *deploy method* select the **github** option. You will need to add this [repo](https://github.com/juanitodread/crud-playframework.git) to link the code to heroku and automatically heroku will deploy on each new commit.
5. If you want to add changes to the code send a pull request.
