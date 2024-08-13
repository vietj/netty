/*
 * Copyright 2012 The Netty Project
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
package io.netty.testsuite_jpms.main;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.IoHandlerFactory;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.Channel;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.ChannelOption;
import io.netty.channel.kqueue.KQueueIoHandler;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.IdentityCipherSuiteFilter;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.netty.handler.ssl.SslContextBuilder.forServer;

/**
 * An HTTP server that sends back the content of the received HTTP request
 * in a pretty plaintext form.
 */
public final class HttpHelloWorldServer {

    private HttpHelloWorldServer() {
    }

    private static File unpackFile(String fileName) throws Exception {
        InputStream res = HttpHelloWorldServer.class.getResourceAsStream(fileName);
        byte[] content = res.readAllBytes();
        Path temp = Files.createTempFile(fileName, null);
        File file = temp.toFile();
        FileOutputStream out = new FileOutputStream(file);
        out.write(content);
        out.close();
        return file;
    }

    // Running SSL with openssl static
    // ./target/maven-jlink/default/bin/java
    // --add-modules io.netty.tcnative.classes.openssl,io.netty.internal.tcnative.openssl.osx.aarch_64
    // -m io.netty.testsuite_jpms.main/io.netty.testsuite_jpms.main.HttpHelloWorldServer --ssl

    // Running with KQueue
    // ./target/maven-jlink/default/bin/java --add-modules io.netty.transport.kqueue.osx.aarch_64
    // -m io.netty.testsuite_jpms.main/io.netty.testsuite_jpms.main.HttpHelloWorldServer
    // --transport kqueue
    public static void main(String[] args) throws Exception {

        String transport = "nio";
        boolean ssl = false;
        Integer port = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--ssl")) {
                ssl = true;
            }
            if (args[i].equals("--port")) {
                if (i < args.length - 1) {
                    port = Integer.parseInt(args[i + 1]);
                } else {
                    System.exit(1);
                }
            }
            if (args[i].equals("--transport")) {
                if (i < args.length - 1) {
                    transport = args[i + 1];
                } else {
                    System.exit(1);
                }
            }
        }

        if (port == null) {
            port = ssl ? 8443 : 8080;
        }

        IoHandlerFactory ioHandlerFactory;
        Class<? extends ServerSocketChannel> serverSocketChannelFactory;
        switch (transport) {
            case "nio":
                ioHandlerFactory = NioIoHandler.newFactory();
                serverSocketChannelFactory = NioServerSocketChannel.class;
                break;
            case "kqueue":
                ioHandlerFactory = KQueueIoHandler.newFactory();
                serverSocketChannelFactory = KQueueServerSocketChannel.class;
                break;
            default:
                System.exit(1);
                return;
        }

        SslContext sslContext;
        if (ssl) {
            File serverCert = unpackFile("localhost_server.pem");
            File serverKey = unpackFile("localhost_server.key");
            sslContext = forServer(serverCert, serverKey, null)
                    .sslProvider(SslProvider.JDK)
                    .protocols("TLSv1.2")
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .ciphers(null, IdentityCipherSuiteFilter.INSTANCE)
                    .sessionCacheSize(0)
                    .sessionTimeout(0)
                    .build();
        } else {
            sslContext = null;
        }

        // Configure the server.
        EventLoopGroup bossGroup = new MultiThreadIoEventLoopGroup(1, ioHandlerFactory);
        EventLoopGroup workerGroup = new MultiThreadIoEventLoopGroup(ioHandlerFactory);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup)
             .channel(serverSocketChannelFactory)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new HttpHelloWorldServerInitializer(sslContext));

            Channel ch = b.bind(port).sync().channel();

            System.err.println("Open your web browser and navigate to " +
                    (ssl? "https" : "http") + "://127.0.0.1:" + port + '/');

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
