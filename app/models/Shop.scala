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
package models

import scala.collection.concurrent.TrieMap
import java.util.concurrent.atomic.AtomicLong

/**
 *
 *
 * @author juanitodread
 * @version $
 *
 * May 1, 2015
 */
trait Shop {

  def getItems(): List[Item]
  
  def createItem(name: String, price: Double): Option[Item]
  
  def getItem(id: Long): Option[Item]
  
  def updateItem(id: Long, name: String, price: Double): Option[Item]
  
  def deleteItem(id: Long): Boolean
  
}

object Shop extends Shop {
  private val items = TrieMap.empty[Long, Item]
  private val seq = new AtomicLong
  
  def getItems() = items.values.toList
  
  def createItem(name: String, price: Double) = {
    val id = seq.incrementAndGet
    val item = Item(id, name, price)
    items.put(id, item)
    Some(item)
  }
  
  def getItem(id: Long) = items.get(id)
  
  def updateItem(id: Long, name: String, price: Double) = {
    val item = Item(id, name, price)
    items.replace(id, item)
    Some(item)
  }
  
  def deleteItem(id: Long) = items.remove(id).isDefined
}