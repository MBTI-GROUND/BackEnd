package MBTI_GROUND.toypj.Entity;

import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class  CommentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "POSTENTITY_ID")
  private PostEntity postEntity;
  @ManyToOne
  @JoinColumn(name = "USERENTITY_ID")
  private UserEntity userEntity;
  private String comment;
  private int likeCount;
  private int hateCount;
  @CreatedDate
  private LocalDateTime createDate;


}
