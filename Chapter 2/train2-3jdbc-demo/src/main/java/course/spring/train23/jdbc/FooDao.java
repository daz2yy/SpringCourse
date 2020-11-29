package course.spring.train23.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class FooDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;
    
    public void insertData() {
        // use jdbc
        Arrays.asList("a", "b").forEach(row -> {
            jdbcTemplate.update("INSERT INTO FOO (BAR) VALUES (?)", row);
        });
        
        // use simpleJdbc
        Map<String, String> map = new HashMap<>(1);
        map.put("BAR", "d");
        Number id = simpleJdbcInsert.executeAndReturnKey(map);
        log.info("ID of d: {}", id);
    }
    
    public void listData() {
        // normal
        // jdbcTemplate.queryForList("SELECT * FROM FOO").forEach(System.out::println);
        
        List<String> list = jdbcTemplate.queryForList("SELECT BAR FROM FOO", String.class);
        list.forEach(bar -> log.info("listData bar: {}", bar));
        
        // RowMapper
        List<Foo> fooList = jdbcTemplate.query("SELECT * FROM FOO", new RowMapper<Foo>() {
            @Override
            public Foo mapRow(ResultSet resultSet, int i) throws SQLException {
                return Foo.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .build();
            }
        });
        list.forEach(foo -> log.info("listData Foo: {}", foo));
    }
}
