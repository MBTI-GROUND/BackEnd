package MBTI_GROUND.toypj.Dto;

import MBTI_GROUND.toypj.Entity.CommentEntity;
import MBTI_GROUND.toypj.Entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private String nickname;
    private String type;
    private String contents;
    private int likeCount;
    private int hateCount;
    private LocalDateTime createdTime;
    private List<CommentEntity> comments;


    public static PostResponseDto of(PostEntity postEntity){
        return PostResponseDto.builder()
                .id(postEntity.getId())
                .contents(postEntity.getContents())
                .type(postEntity.getType())
                .likeCount(postEntity.getLikeCount())
                .hateCount(postEntity.getHateCount())
                .comments(postEntity.getCommentEntities())
                .nickname(postEntity.getUserEntity().getNickname())
                .createdTime(postEntity.getCreatedDate())
                .build();

    }
}
