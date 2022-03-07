package MBTI_GROUND.toypj.Dto;

import MBTI_GROUND.toypj.Entity.PostEntity;
import MBTI_GROUND.toypj.Entity.UserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequestDto {
    @ApiModelProperty(example = "게시글 ID")
    private Long id;
    @ApiModelProperty(example = "게시글 TYPE(MBTI)")
    private String type;
    @ApiModelProperty(example = "게시글 제목")
    private String title;
    @ApiModelProperty(example = "게시글 내용")
    private String contents;
    @ApiModelProperty(example = "작성자 ID")
    private String writerId;


    public PostEntity toPostEntity(UserEntity userEntity){

        return PostEntity.builder()
                .type(this.getType())
                .title(this.getTitle())
                .userEntity(userEntity)
                .contents(this.getContents())
                .build();
    }







}
