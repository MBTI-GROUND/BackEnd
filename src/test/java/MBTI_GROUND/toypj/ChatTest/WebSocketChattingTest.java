package MBTI_GROUND.toypj.ChatTest;

import MBTI_GROUND.toypj.Entity.ChatRoomEntity;
import MBTI_GROUND.toypj.Entity.UserEntity;
import MBTI_GROUND.toypj.Repository.ChatRoomRepository;
import MBTI_GROUND.toypj.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class WebSocketChattingTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @BeforeEach
    void setUp() {

        유저_삽입();
        방_생성();
    }

    private void 유저_삽입() {
        userRepository.save(UserEntity.builder().email("와일더").build());
        userRepository.save(UserEntity.builder().email("마이클").build());
        userRepository.save(UserEntity.builder().email("제이스").build());
        userRepository.save(UserEntity.builder().email("이렐리아").build());
    }

    private void 방_생성() {
        chatRoomRepository.save(ChatRoomEntity.builder().roomId("1").build());
        chatRoomRepository.save(ChatRoomEntity.builder().roomId("2").build());
        chatRoomRepository.save(ChatRoomEntity.builder().roomId("3").build());
    }

    @DisplayName("유저가 입장하고 메시지를 보내면 해당 방에 메시지가 브로드 캐스팅된다.")
    @Test
    void enterUserAndBroadCastMessage() throws ExecutionException, InterruptedException, TimeoutException {
        Optional<ChatRoomEntity> chatRoomByRoomId = chatRoomRepository.findChatRoomByRoomId("1");
        Optional<UserEntity> user = userRepository.findByEmail("와일더");

        // Settings
        WebSocketStompClient stompclient = getSTOMPCLIENT();
        stompclient.setMessageConverter(new MappingJackson2MessageConverter());

        //connection
        ListenableFuture<StompSession> connect = stompclient.connect("ws://localhost:" + port + "/ws", new StompSessionHandlerAdapter() {
        });
        StompSession stompSession = connect.get(60, TimeUnit.SECONDS);

    }

    private WebSocketStompClient getSTOMPCLIENT() {
        StandardWebSocketClient standardWebSocketClient = new StandardWebSocketClient();
        WebSocketTransport webSocketTransport = new WebSocketTransport(standardWebSocketClient);
        List<Transport> transports = Collections.singletonList(webSocketTransport);
        SockJsClient sockJsClient = new SockJsClient(transports);

        return new WebSocketStompClient(sockJsClient);
    }


}
