package MBTI_GROUND.toypj.Api;

import MBTI_GROUND.toypj.Dto.ChatRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations messageSendingOperations;

    @MessageMapping("/message")
    public void chat(@Valid ChatRequestDto message){
        if(ChatRequestDto.MessageType.ENTER.equals(message.getMessageType())){

            message.setMessage(message.getSender()+"님이 입장하셨습니다.");
            messageSendingOperations.convertAndSend("/sub/chat/room/"+message.getRoomId(),message.getMessage());

        }

    }
}
