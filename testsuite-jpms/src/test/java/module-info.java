open module io.netty.testsuite_jpms.test {
    requires org.slf4j;
    requires ch.qos.logback.core;
    requires ch.qos.logback.classic;
    requires org.junit.jupiter.api;
    requires io.netty.buffer;
    requires io.netty.codec;
    requires io.netty.codec.xml;
    requires io.netty.codec.smtp;
    requires io.netty.handler;
    requires io.netty.transport.classes.kqueue;
    requires io.netty.resolver.dns.classes.macos;
    requires io.netty.resolver.dns;
}