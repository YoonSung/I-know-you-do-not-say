package gaongil.dao;

import static org.junit.Assert.*;
import gaongil.config.AppCoinfig;
import gaongil.domain.User;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppCoinfig.class})
public class UserDaoTest {

	@Autowired
	UserDao userDao;
	
	User user1;
	
	@Before
	public void setUp() {
		user1 = new User("01099258547", "testNickname", "testRegistrationId");
	}
	
	@Test
	public void userDao() throws Exception {
		assertNotNull(userDao);
	}
	
	@Test
	public void create() throws Exception {
		userDao.deleteAll();
		userDao.create(user1);
	}
	
	@Test
	public void select() throws Exception {
		userDao.deleteAll();
		userDao.create(user1);
		
		User selectedUser = userDao.findByRegId(user1.getRegId());
		System.out.println("selectedUser : "+selectedUser.toString());
		assertEquals(user1.getNickname(), selectedUser.getNickname());
	}
}
