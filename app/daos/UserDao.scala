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
package daos

import models._
import models.JsonFormats._

import scala.concurrent.Future

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json

import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection.JSONCollection

import reactivemongo.api.commands.WriteResult

import scala.collection.mutable.ListBuffer

trait UserDao {
  def find(): Future[List[User]]

  def findById(id: String): Future[Option[User]]

  def save(user: TransientUser): Future[WriteResult]

  def update(id: String, user: TransientUser): Future[WriteResult]

  def delete(id: String): Future[WriteResult]
}

class MongoUserDao(reactiveMongoApi: ReactiveMongoApi) extends UserDao {

  protected def usersCollection = reactiveMongoApi.db.collection[JSONCollection]("users")

  def find(): Future[List[User]] = {
    usersCollection.find(Json.obj()).cursor[User]().collect[List]()
  }

  def findById(id: String): Future[Option[User]] = {
    usersCollection.find(Json.obj("_id" -> Json.obj("$oid" -> id))).one[User]
  }

  def save(user: TransientUser): Future[WriteResult] = {
    usersCollection.insert(user)
  }

  def update(id: String, user: TransientUser): Future[WriteResult] = {
    usersCollection.update(Json.obj("_id" -> Json.obj("$oid" -> id)), user)
  }

  def delete(id: String): Future[WriteResult] = {
    usersCollection.remove(Json.obj("_id" -> Json.obj("$oid" -> id)))
  }
}

object MongoUserDao {
  def apply(reactiveMongoApi: ReactiveMongoApi) = new MongoUserDao(reactiveMongoApi)
}

class MemoryUserDao {
  def find(): List[User] = {
    MemoryUserDao.Users.toList
  }

  def findById(id: String): Option[User] = {
    Some(MemoryUserDao.Users.filter(user => user._id.$oid == id).head)
  }

  def save(user: User): Unit = {
    MemoryUserDao.Users += user
  }

  def update(id: String, user: User): Unit = {
    val index = MemoryUserDao.Users.zipWithIndex.filter(_._1._id.$oid == id).map(_._2).head
    MemoryUserDao.Users(index) = user
  }

  def delete(id: String): Unit = {
    MemoryUserDao.Users = MemoryUserDao.Users.filter(user => user._id.$oid != id)
  }
}

object MemoryUserDao {
  var Users = ListBuffer[User](
    User(ObjectId("1"), "Juan", 29, "juan@mail.com"),
    User(ObjectId("2"), "Antonio", 32, "antonio@mail.com")
  )
}
