open module io.netty.testsuite_jpms.test {
    requires org.slf4j;
    requires ch.qos.logback.core;
    requires ch.qos.logback.classic;
    requires org.junit.jupiter.api;
    requires io.netty.buffer;
    requires io.netty.codec;
    requires io.netty.codec.xml;
    requires io.netty.codec.smtp;
    requires io.netty.codec.mqtt;
    requires io.netty.codec.memcache;
    requires io.netty.codec.haproxy;
    requires io.netty.codec.redis;
    requires io.netty.codec.stomp;
    requires io.netty.codec.socks;
    requires io.netty.codec.protobuf;
    requires io.netty.codec.marshalling;
    requires io.netty.handler;
    requires io.netty.handler.ssl.ocsp;
    requires io.netty.transport.classes.kqueue;
    requires io.netty.transport.classes.epoll;
    requires io.netty.resolver.dns.classes.macos;
    requires io.netty.resolver.dns;
    requires io.netty.codec.http;
    requires io.netty.codec.http2;
    requires jboss.marshalling;
    requires org.bouncycastle.pkix;
}