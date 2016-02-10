/**
 * simple-crud
 *
 * Copyright 2016 juanitodread
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package controllers

import javax.inject.Inject

import scala.concurrent.Future

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import org.slf4j.{
  LoggerFactory,
  Logger
}

import daos._
import models._
import play.api.libs.json._
import models.JsonFormats._

import play.modules.reactivemongo.{
  MongoController,
  ReactiveMongoApi,
  ReactiveMongoComponents
}

class UserController @Inject()(val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[UserController])

  val userDao = MongoUserDao(reactiveMongoApi)

  def getAll() = Action.async {
    logger.info("getAll()")
     val futureUserList: Future[List[User]] = userDao.find

     futureUserList.map { users =>
       Ok(Json.toJson(users))
     }
  }

  def getUserById(id: String) = Action.async {
    logger.info(s"getUserById($id)")
    userDao.findById(id).map(user => user match {
      case Some(user) => Ok(Json.toJson(user))
      case None       => NotFound
    })
  }

  def create() = Action.async(parse.json) { request =>
    logger.info("create()")
    logger.info(s"${request.body}")
    request.body.validate[TransientUser].map {
      user => userDao.save(user).map { lastError =>
        logger.debug(s"User created with LastError: $lastError")
        Created
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def update(id: String) = Action.async(parse.json) { request =>
    logger.info(s"update($id)")
    logger.debug(s"${request.body}")
    request.body.validate[TransientUser].map {
      user => userDao.update(id, user).map { lastError =>
        logger.debug(s"User updated with LastError: $lastError")
        Ok
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def delete(id: String) = Action.async {
    logger.info( s"delete($id)" )
    userDao.delete(id).map { lastError =>
      logger.debug(s"User updated with LastError: $lastError")
      Ok
    }
  }

}
