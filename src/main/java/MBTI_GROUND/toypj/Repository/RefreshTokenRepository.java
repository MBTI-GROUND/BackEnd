package MBTI_GROUND.toypj.Repository;

import MBTI_GROUND.toypj.Entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByUserId(String userId);

}
