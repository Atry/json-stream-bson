/*
 * json-stream-bson
 * Copyright 2014 深圳岂凡网络有限公司 (Shenzhen QiFun Network Corp., LTD)
 * 
 * Author: 杨博 (Yang Bo) <pop.atry@gmail.com>, 张修羽 (Zhang Xiuyu) <zxiuyu@126.com>
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
 */

package com.qifun.jsonStream.bsonStream

import reactivemongo.api.collections.buffer.RawBSONDocumentSerializer
import com.qifun.jsonStream.JsonStream
import reactivemongo.core.netty.ChannelBufferWritableBuffer
import reactivemongo.bson.BSONDocument
import com.qifun.jsonStream.io.BsonWriter
import scala.util.control.Exception

class BsonStreamSerializer[T](
  haxeSerializeFunction: T => JsonStream) extends RawBSONDocumentSerializer[T] {
  def serialize(obj: T) = {
    val writeableBuffer = new ChannelBufferWritableBuffer
    val jsonStream = haxeSerializeFunction(obj)
    writeableBuffer.writeInt(0);
    writeableBuffer.writeByte(0x03);
    writeableBuffer.writeCString("Content");
    BsonWriter.writeBsonObject(writeableBuffer, jsonStream.params.__get(0))
    writeableBuffer.setInt(0, writeableBuffer.index + 1)
    writeableBuffer.writeByte(0)
    writeableBuffer
  }
}
