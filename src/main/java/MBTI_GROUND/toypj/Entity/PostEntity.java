package MBTI_GROUND.toypj.Entity;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERENTITY_ID")
    private UserEntity userEntity;
    private String type;
    private String title;
    private String contents;
    @OneToMany(mappedBy = "postEntity")
    private List<CommentEntity> commentEntities = new ArrayList<>();
    private int likeCount;
    private int hateCount;


    @Builder
    public PostEntity(String title, String contents, String type, UserEntity userEntity) {
        this.title = title;
        this.userEntity = userEntity;
        this.contents = contents;
        this.type = type;
        this.likeCount = 0;
        this.hateCount = 0;
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void updateLikeCount(int count) {
        this.likeCount += count;
        if (this.likeCount < 0) {
            this.likeCount = 0;
        }
    }

    public void updateHateCount(int count) {
        this.hateCount += count;
        if (this.hateCount < 0) {
            this.hateCount = 0;
        }
    }


}
