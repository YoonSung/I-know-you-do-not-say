package gaongil.repository;

import gaongil.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query("SELECT u FROM User u WHERE reg_id = :reg_id")
	User findByRegId(@Param("reg_id") String regId);

	@Query("SELECT u FROM User u WHERE phone_number = :phone_number")
	User findByPhoneNumber(@Param("phone_number") String phoneNumber);
}