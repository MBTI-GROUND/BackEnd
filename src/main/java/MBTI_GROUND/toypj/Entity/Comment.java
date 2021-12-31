package MBTI_GROUND.toypj.Entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

  @Id
  @GeneratedValue
  private Long id;
  private Long postId;
  private Long userId;
  private String comment;
  @Column(columnDefinition = "INTEGER default 0")
  private int like;
  @Column(columnDefinition = "INTEGER default 0")
  private int hate;
  @CreatedDate
  private LocalDateTime createDate;


}
