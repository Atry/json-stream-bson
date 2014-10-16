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

import reactivemongo.api.collections.buffer.RawBSONDocumentDeserializer
import com.qifun.jsonStream.JsonStream
import reactivemongo.core.netty.ChannelBufferWritableBuffer
import reactivemongo.bson.buffer.ReadableBuffer
import reactivemongo.bson.BSONDocument
import com.qifun.jsonStream.io.BsonReader
import reactivemongo.bson.buffer.DefaultBufferHandler

class BsonStreamDeserializer[T](
  haxeDeserializeFunction: JsonStream => T) extends RawBSONDocumentDeserializer[T] {
  def deserialize(buffer: ReadableBuffer): T = {
    val toJsonStreamBuffer = new ChannelBufferWritableBuffer
    val length = buffer.readInt()
    while (buffer.readable > 1) //the last byte of buffer is 0x0
    {
      val code = buffer.readByte()
      val name = buffer.readCString()
      if (code == 0x03 || name == "Content") {
        val contentLength = buffer.readInt()
        toJsonStreamBuffer.writeInt(contentLength)
        toJsonStreamBuffer.writeBytes(buffer.slice(contentLength - 4))
        toJsonStreamBuffer.writeByte(0)
      } else {
        DefaultBufferHandler.handlersByCode.get(code).map(_.read(buffer))
      }
    }

    val jsonStream = BsonReader.readBsonStream(toJsonStreamBuffer.toReadableBuffer)
    haxeDeserializeFunction(jsonStream)
  }
}
