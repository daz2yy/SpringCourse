package course.spring.train23.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class BatchFooDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    public void batchInsert_jdbc() {
        log.info("batchInsert_jdbc ===========>");
        jdbcTemplate.batchUpdate("INSERT INTO FOO (BAR) VALUES (?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setString(1, "batch-" + i);
                    }
    
                    @Override
                    public int getBatchSize() {
                        return 3;
                    }
                });
    }
    
    public void batchInsert_NameParamJdbc() {
        log.info("batchInsert_NameParamJdbc ===========>");
        List<Foo> list = new ArrayList<>(2);
        list.add(Foo.builder().id(101).name("batchName-101").build());
        list.add(Foo.builder().id(102).name("batchName-102").build());
        namedParameterJdbcTemplate.batchUpdate("INSERT INTO FOO (ID, BAR) VALUES (:id, :name)",
                SqlParameterSourceUtils.createBatch(list));
    }
}
