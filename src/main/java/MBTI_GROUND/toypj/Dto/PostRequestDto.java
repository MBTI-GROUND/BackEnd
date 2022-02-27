package MBTI_GROUND.toypj.Dto;

import MBTI_GROUND.toypj.Entity.PostEntity;
import MBTI_GROUND.toypj.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequestDto {

    private Long id;
    private String type;
    private String title;
    private String contents;
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
