package me.levansj01.verus.compat.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.DefaultThreadFactory;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;
import me.levansj01.verus.data.bytes.ByteData;

public class UnshadedNettyHandler implements NettyHandler<Channel> {
    @Override
    public ByteData wrap(byte[] bytes, int value, int value2) {
        return new UnshadedByteData(Unpooled.wrappedBuffer(bytes, value, value2));
    }

    public void inject(Channel channel, Consumer<Object> consumer) {
        channel.eventLoop().execute(() -> {
            ChannelPipeline pipeline = channel.pipeline();
            if (pipeline.get("decoder") != null && pipeline.get("packet_handler") != null) {
                pipeline.addAfter("decoder", "verus_handler_in", new UnshadedNettyHandler.PacketHandlerIn(consumer));
                pipeline.addBefore("packet_handler", "verus_handler_out", new UnshadedNettyHandler.PacketHandlerOut(consumer));
            }
        });
    }

    @Override
    public ByteData of(int value) {
        return new UnshadedByteData(Unpooled.directBuffer(value, value).setZero(0, value));
    }

    public void uninject(Channel playerData) {
        playerData.eventLoop().execute(() -> {
            ChannelPipeline pipeline = playerData.pipeline();
            if (pipeline.get("verus_handler_in") != null) {
                pipeline.remove("verus_handler_in");
            }

            if (pipeline.get("verus_handler_out") != null) {
                pipeline.remove("verus_handler_out");
            }
        });
    }

    @Override
    public ThreadFactory newThreadFactory(String str, boolean flag, int value) {
        return new DefaultThreadFactory(str, flag, value);
    }

    public static class PacketHandlerIn extends ChannelInboundHandlerAdapter {
        private final Consumer<Object> listener;

        public PacketHandlerIn(Consumer<Object> listener) {
            this.listener = listener;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            super.channelRead(ctx, msg);
            this.listener.accept(msg);
        }
    }

    public static class PacketHandlerOut extends ChannelOutboundHandlerAdapter {
        private final Consumer<Object> listener;

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            super.write(ctx, msg, promise);
            this.listener.accept(msg);
        }

        public PacketHandlerOut(Consumer<Object> listener) {
            this.listener = listener;
        }
    }
}
