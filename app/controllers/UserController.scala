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

import play.api._
import play.api.mvc._
import org.slf4j.{LoggerFactory, Logger}
import daos._
import models._
import play.api.libs.json._
import models.JsonFormats._

class UserController extends Controller {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[UserController])

  val userDao = new MemoryUserDao

  def getAll() = Action {
    logger.info("getAll()")
    val users = userDao.find
    val jsonUsers = Json.toJson(users)
    logger.info(jsonUsers.toString)
    Ok(jsonUsers).withHeaders(
      "access-control-allow-origin" -> "*"
    )
  }

  def getUserById(id: String) = Action {
    logger.info(s"getUserById($id)")
    userDao.findById(id) match {
      case Some(x) => Ok(Json.toJson(x))
      case None => NotFound
    }
  }

  def create() = Action(parse.json) { request =>
    logger.info("create()")
    logger.info(s"${request.body}")
    request.body.validate[User].map {
      user => userDao.save(user)
      NoContent
    }.getOrElse(BadRequest("invalid json"))
  }

  def update(id: String) = Action(parse.json) { request =>
    logger.info(s"update($id)")
    logger.info(s"${request.body}")
    request.body.validate[User].map {
      user => userDao.update(id, user)
      NoContent
    }.getOrElse(BadRequest("invalid json"))
  }

  def delete(id: String) = Action {
    logger.info( s"delete($id)" )
    userDao.delete(id)
    NoContent
  }

}
