package app.btyd.repository;

import app.btyd.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

import static app.btyd.util.EntityUtils.slim;

@Repository
public class UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> selectUserList(int limit, int offset) {
        var sql = """
                SELECT id, username, email, avatar_url, last_active_time, creation_time
                FROM t_user
                LIMIT :limit OFFSET :offset;
                """;
        var paramMap = Map.of("limit", limit, "offset", offset);
        var rowMapper = BeanPropertyRowMapper.newInstance(User.class);
        return this.jdbcTemplate.query(slim(sql), paramMap, rowMapper);
    }

    public Integer selectUserCount() {
        var sql = """
                SELECT COUNT(*)
                FROM t_user;
                """;
        var rowMapper = SingleColumnRowMapper.newInstance(Integer.class);
        var count = this.jdbcTemplate.query(slim(sql), rowMapper);
        Assert.notEmpty(count, "Illegal count select result");
        return count.get(0);
    }
}
