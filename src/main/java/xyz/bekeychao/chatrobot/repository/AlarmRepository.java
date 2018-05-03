package xyz.bekeychao.chatrobot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.bekeychao.chatrobot.domain.Alarm;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, String> {
    List<Alarm> getAllByUserId(String userId);
}
