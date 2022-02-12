package MBTI_GROUND.toypj.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String roomId;

    @Builder
    public ChatRoomEntity (String roomId){
        this.roomId = roomId;
    }
}
