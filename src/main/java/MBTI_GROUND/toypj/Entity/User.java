package MBTI_GROUND.toypj.Entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Id
  @GeneratedValue
  private Long id;
  private String userId;
  private String password;
  private String username;
  private String role;
  private String email;
  private String phoneNumber;
  private MBTI mbti;
  @CreatedDate
  private LocalDateTime createdDate;



}
