package gaongil.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import gaongil.domain.User;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

//@Repository
public class UserDao extends JdbcDaoSupport {
	
	/*
	@Autowired
	private DataSource dataSource;
	*/
	
	RowMapper<User> rowMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			return new User(
					rs.getInt("pid"),
					rs.getString("phone_number"),
					rs.getBoolean("is_member"),
					rs.getString("nickname"),
					rs.getString("image_path"),
					rs.getString("reg_id")
			);
		}
	};
	
	@PostConstruct
	public void init() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("test.sql"));
		DatabasePopulatorUtils.execute(populator, getDataSource());
	}
	
	public void create(User user) {
		String sql = "INSERT INTO tbl_user(phone_number, nickname, reg_id) VALUES(?, ?, ?)";
		getJdbcTemplate().update(sql, user.getPhoneNumber(), user.getNickname(), user.getRegistrationId());
	}

	public User findByRegId(String regId) {
		String sql = "SELECT * FROM tbl_user WHERE reg_id = ?";
		return getJdbcTemplate().queryForObject(sql, rowMapper, regId);
	}

	public void deleteAll() {
		String sql = "DELETE FROM tbl_user";
		getJdbcTemplate().update(sql);
	}
}
