package MBTI_GROUND.toypj.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookMarkEntity {
  @Id
  @GeneratedValue
  private Long id;
  private Long userId;
  private Long postId;


}
