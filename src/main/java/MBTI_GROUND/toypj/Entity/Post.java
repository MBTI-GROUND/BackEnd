package MBTI_GROUND.toypj.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Post {
  @Id
  @GeneratedValue
  private Long id;
  private Long writerId;
  private String type;
  private String title;
  private String contents;
  @Column(columnDefinition = "INTEGER default 0")
  private int like;
  @Column(columnDefinition = "INTEGER default 0")
  private int hate;


}
