package MBTI_GROUND.toypj.Entity;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "USERENTITY_ID")
  private UserEntity userEntity;
  private String type;
  private String title;
  private String contents;
  @OneToMany(mappedBy = "postEntity")
  private List<CommentEntity> commentEntities = new ArrayList<>();
  private int likeCount;
  private int hateCount;


}
