module io.netty.testsuite_jpms.main {
    requires io.netty.buffer;
    requires io.netty.codec;
    requires io.netty.codec.http;
    requires io.netty.handler;
    requires io.netty.transport;
    requires io.netty.transport.classes.kqueue;
    requires io.netty.transport.kqueue.osx.aarch_64;
}