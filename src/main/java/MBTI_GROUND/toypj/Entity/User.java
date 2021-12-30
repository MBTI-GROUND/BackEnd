package MBTI_GROUND.toypj.Entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.springframework.data.annotation.CreatedDate;

@Entity
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
