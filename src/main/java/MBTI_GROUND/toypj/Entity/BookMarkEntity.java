package MBTI_GROUND.toypj.Entity;

import javax.persistence.*;

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
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USERENTITY_ID")
  private UserEntity userEntity;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "POSTENTITY_ID")
  private PostEntity postEntity;


}
