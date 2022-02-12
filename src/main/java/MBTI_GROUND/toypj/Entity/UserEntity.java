package MBTI_GROUND.toypj.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private MBTI mbti;
    private String nickname;
    @CreatedDate
    private LocalDateTime createdDate;

    public enum MBTI{
        ISTJ, ISFJ, INFJ, INTJ, ISTP, ISFP, INFP, INTP, ESTP, ESFP, ENFP, ENTP, ESTJ, ESFJ, ENFJ, ENTJ, NONE
    }

    @Builder
    public UserEntity(String email, String password, String phoneNumber, String mbti, String nickname) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.mbti = MBTI.valueOf(mbti);
        this.nickname = nickname;
    }


}
