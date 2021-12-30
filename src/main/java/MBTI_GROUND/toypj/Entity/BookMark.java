package MBTI_GROUND.toypj.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BookMark {
  @Id
  @GeneratedValue
  private Long id;
  private Long userId;
  private Long postId;
  

}
