package MBTI_GROUND.toypj.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {

    private String nickname;
    private int likeCount;
    private int hateCount;
    private String comment;
}
