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
module io.netty.common {
    requires jdk.unsupported;
    requires java.logging;

    requires static org.apache.commons.logging;
    requires static org.apache.log4j;
    requires static org.apache.logging.log4j;
    requires static org.jetbrains.annotations;
    requires static org.slf4j;

    exports io.netty.util;
    exports io.netty.util.collection;
    exports io.netty.util.concurrent;
    exports io.netty.util.internal;
    exports io.netty.util.internal.logging;
    exports io.netty.util.internal.shaded.org.jctools.counters;
    exports io.netty.util.internal.shaded.org.jctools.maps;
    exports io.netty.util.internal.shaded.org.jctools.queues;
    exports io.netty.util.internal.shaded.org.jctools.queues.atomic;
    exports io.netty.util.internal.shaded.org.jctools.queues.atomic.unpadded;
    exports io.netty.util.internal.shaded.org.jctools.queues.unpadded;
    exports io.netty.util.internal.shaded.org.jctools.util;
}