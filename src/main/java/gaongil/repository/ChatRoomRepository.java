package gaongil.repository;

import gaongil.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yoon on 15. 7. 1..
 */
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
