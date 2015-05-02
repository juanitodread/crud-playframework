/**
 * simple-crud
 *
 * Copyright 2015 juanitodread
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

import play.api.mvc.{Controller, Action}
import models.Shop
import play.api.libs.json.Json
import play.api.libs.json.JsObject
import play.api.libs.json.Writes

/**
 *
 *
 * @author juanitodread
 * @version $
 *
 * May 2, 2015
 */
object Item extends Controller {

  val shop = Shop
  
  implicit val writesItem = Writes[models.Item] {
    case models.Item(id, name, price) => 
      Json.obj(
        "id" -> id,
        "name" -> name,
        "price" -> price
      )
  }
  
  val items = Action { 
    Ok(Json.toJson(shop.getItems))
  }
  
  val create = Action { NotImplemented }
  
  def item(id: Long) = Action { 
    shop.getItem(id) match {
      case Some(item) => {
        Ok(Json.toJson(item))
      }
      case None => NotFound
    }
  }
  
  def update(id: Long) = Action { NotImplemented }
  
  def delete(id: Long) = Action { NotImplemented }
  
}