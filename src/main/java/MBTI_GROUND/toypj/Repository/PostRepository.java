package MBTI_GROUND.toypj.Repository;

import MBTI_GROUND.toypj.Entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity,Long> {

    public List<PostEntity> findAllByTypeOrderByCreatedDateDesc(String type);


}
