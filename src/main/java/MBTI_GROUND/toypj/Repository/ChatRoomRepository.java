package MBTI_GROUND.toypj.Repository;

import MBTI_GROUND.toypj.Entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity,Long> {

    Optional<ChatRoomEntity> findChatRoomByRoomId(String roomId);





}
