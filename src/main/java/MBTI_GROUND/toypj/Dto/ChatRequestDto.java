package MBTI_GROUND.toypj.Dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class ChatRequestDto {

    public enum MessageType{
        ENTER, TALK
    }
    private MessageType messageType;
    private String roomId;
    private String sender;
    private String message;
}
