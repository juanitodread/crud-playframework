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

case class TransientUser(
  name: String,
  age: Int,
  email: String
)

case class User(
  _id: ObjectId,
  name: String,
  age: Int,
  email: String
)

case class ObjectId($oid: String)

object JsonFormats {
  import play.api.libs.json.Json

  implicit val objectIdFormat = Json.format[ObjectId]
  implicit val userFormat = Json.format[User]
  implicit val transientUserFormat = Json.format[TransientUser]
}
