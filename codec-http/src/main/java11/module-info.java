/*
 * Copyright 2024 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
module io.netty.codec.http {
    requires io.netty.common;
    requires io.netty.buffer;
    requires io.netty.transport;
    requires io.netty.codec;
    requires io.netty.handler;
    requires static io.netty.codec.compression;
    exports io.netty.handler.codec.http;
    exports io.netty.handler.codec.http.cookie;
    exports io.netty.handler.codec.http.cors;
    exports io.netty.handler.codec.http.multipart;
    exports io.netty.handler.codec.http.websocketx;
    exports io.netty.handler.codec.http.websocketx.extensions;
    exports io.netty.handler.codec.http.websocketx.extensions.compression;
}