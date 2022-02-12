package MBTI_GROUND.toypj.Auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE+99)
public class AuthenticationChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("AuthenticationChannelInterceptor is running");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println("message = " + message);
        System.out.println("message.getHeaders() = " + message.getHeaders());
        System.out.println("message.getPayload() = " + message.getPayload());
        System.out.println("accessor.getNativeHeader(\"Authorization\") = " + accessor.getNativeHeader("Authorization"));
        return ChannelInterceptor.super.preSend(message, channel);
    }
}
