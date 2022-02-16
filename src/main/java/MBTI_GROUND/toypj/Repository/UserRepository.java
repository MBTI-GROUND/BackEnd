package MBTI_GROUND.toypj.Repository;

import MBTI_GROUND.toypj.Entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

  Optional<UserEntity> findByEmailAndPassword(String Email,String Password);
  Optional<UserEntity> findByPhoneNumber(String phoneNumber);
  Optional<UserEntity> findByNickname(String nickName);
  Optional<UserEntity> findByEmail(String email);
  boolean existsByEmail(String email);


}
