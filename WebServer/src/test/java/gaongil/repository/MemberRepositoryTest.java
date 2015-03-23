package gaongil.repository;

import static org.junit.Assert.*;
import gaongil.config.AppCoinfig;
import gaongil.domain.Member;
import gaongil.domain.User;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppCoinfig.class})
@Transactional
public class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;
	
	@Test
	public void findByEmail() {
		Member member = memberRepository.findByEmail("lvev9925@naver.com");
		
		System.out.println(member.toString());
	}

}
