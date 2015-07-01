package gaongil.repository;

import gaongil.domain.ChatRoomSetting;
import gaongil.domain.ChatRoomSettingPK;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yoon on 15. 7. 2..
 */
public interface ChatRoomSettingRepository extends JpaRepository<ChatRoomSetting, ChatRoomSettingPK> {
}
