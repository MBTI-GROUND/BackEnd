package MBTI_GROUND.toypj.Api;

import MBTI_GROUND.toypj.Dto.PostRequestDto;
import MBTI_GROUND.toypj.Dto.PostResponseDto;
import MBTI_GROUND.toypj.Service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/post")
@Api(tags = {"게시판 API"})
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @ApiOperation(value = "게시글 정보 생성",notes = "게시글을 생성하는 API 입니다.")
    @PostMapping("/create")
    public ResponseEntity<?> addPost(@RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postRequestDto));

    }

    @GetMapping("/find/{id}")
    @ApiOperation(value = "특정 게시글 찾기 ",notes = "특정 게시글을 찾는 API 입니다.")
    @ApiImplicitParam(name = "id", value = "게시글 ID")
    public ResponseEntity<?> findPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findOne(id));
    }

    @GetMapping("/find")
    @ApiOperation(value = "특정 게시글 찾기 ",notes = "특정 TYPE(MBTI) 게시글들을 찾는 API 입니다.")
    @ApiImplicitParam(name = "type", value = "게시글 type(MBTI)")
    public ResponseEntity<?> findPostAll(String type) {
        return ResponseEntity.ok(postService.findAll(type));
    }

    @DeleteMapping("/del/{id}")
    @ApiOperation(value = "특정 게시글 삭제 ",notes = "특정 게시글을 삭제하는 API 입니다.")
    @ApiImplicitParam(name = "id", value = "게시글 ID")
    public ResponseEntity<?> delPost(@PathVariable Long id) {
        postService.delPost(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    @ApiOperation(value = "특정 게시글 변경 ",notes = "특정 게시글을 변경하는 API 입니다.")
    public ResponseEntity<?> updPost(@RequestBody PostRequestDto postRequestDto) {
        PostResponseDto postResponseDto = postService.updPost(postRequestDto);
        return ResponseEntity.ok(postResponseDto);
    }

    @PutMapping("/likeCount/plus/{id}")
    @ApiOperation(value = "특정 게시글 좋아요 +1 ",notes = "특정 게시글의 좋아요를 +1하는 API 입니다.")
    @ApiImplicitParam(name = "id", value = "게시글 ID")
    public ResponseEntity<?> likeCountPlus(@PathVariable Long id){
        postService.likeCountPlus(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/likeCount/minus/{id}")
    @ApiOperation(value = "특정 게시글 좋아요 -1 ",notes = "특정 게시글의 좋아요를 -1하는 API 입니다.")
    @ApiImplicitParam(name = "id", value = "게시글 ID")
    public ResponseEntity<?> likeCountMinus(@PathVariable Long id){
        postService.likeCountMinus(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/hateCount/plus/{id}")
    @ApiOperation(value = "특정 게시글 싫어요 +1 ",notes = "특정 게시글의 싫어요를 +1하는 API 입니다.")
    @ApiImplicitParam(name = "id", value = "게시글 ID")
    public ResponseEntity<?> hateCountPlus(@PathVariable Long id){
        postService.hateCountPlus(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/hateCount/minus/{id}")
    @ApiOperation(value = "특정 게시글 싫어요 -1 ",notes = "특정 게시글의 싫어요를 -1하는 API 입니다.")
    @ApiImplicitParam(name = "id", value = "게시글 ID")
    public ResponseEntity<?> hateCountMinus(@PathVariable Long id){
        postService.hateCountMinus(id);
        return ResponseEntity.ok().build();
    }



}
