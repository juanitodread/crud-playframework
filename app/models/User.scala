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
package models

import play.api.libs.json.Writes
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.JsPath
import play.api.libs.functional.syntax._

case class TransientUser(
  name: String,
  age: Int,
  email: String,
  state: UserState.Value
)

case class User(
  _id: ObjectId,
  name: String,
  age: Int,
  email: String,
  state: UserState.Value
)

case class ObjectId($oid: String)

object UserState extends Enumeration {
  val ENABLED = Value(1, "Enabled")
  val DISABLED = Value(2, "Disabled")
}

object JsonFormats {
  import play.api.libs.json.Json

  implicit val objectIdFormat = Json.format[ObjectId]

  // writes
  implicit val UserWrites: OWrites[User] = (
    (JsPath \ "_id").write[ObjectId] and
    (JsPath \ "name").write[String] and
    (JsPath \ "age").write[Int] and
    (JsPath \ "email").write[String] and
    (JsPath \ "state").write[UserState.Value](Writes.enumNameWrites)
  )(unlift(User.unapply))

  implicit val transientUserWrites: OWrites[TransientUser] = (
    (JsPath \ "name").write[String] and
    (JsPath \ "age").write[Int] and
    (JsPath \ "email").write[String] and
    (JsPath \ "state").write[UserState.Value](Writes.enumNameWrites)
  )(unlift(TransientUser.unapply))

  // reads
  implicit val userReads: Reads[User] = (
    (JsPath \ "_id").read[ObjectId] and
    (JsPath \ "name").read[String] and
    (JsPath \ "age").read[Int] and
    (JsPath \ "email").read[String] and
    (JsPath \ "state").read(Reads.enumNameReads(UserState))
  )(User.apply _)

  implicit val transientReads: Reads[TransientUser] = (
    (JsPath \ "name").read[String] and
    (JsPath \ "age").read[Int] and
    (JsPath \ "email").read[String] and
    (JsPath \ "state").read(Reads.enumNameReads(UserState))
  )(TransientUser.apply _)
}
