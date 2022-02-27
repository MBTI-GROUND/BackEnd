package MBTI_GROUND.toypj.Api;

import MBTI_GROUND.toypj.Dto.PostRequestDto;
import MBTI_GROUND.toypj.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/create")
    public ResponseEntity<?> addPost(PostRequestDto postRequestDto) {

        return ResponseEntity.ok(postService.createPost(postRequestDto));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?>  findPost(@PathVariable Long id){
        return ResponseEntity.ok(postService.findOne(id));

    }
    @GetMapping("/find")
    public ResponseEntity<?>  findPostAll(String type){
        return ResponseEntity.ok(postService.findAll(type));
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<?>  delPost(@PathVariable Long id){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?>  updPost(PostRequestDto postRequestDto){
        postService.updPost(postRequestDto);
        return ResponseEntity.ok().build();
    }


}
