package gaongil.repository;

import gaongil.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	@Query("SELECT m FROM Member m WHERE email=:email")
	Member findByEmail(@Param("email") String email);
	
}
