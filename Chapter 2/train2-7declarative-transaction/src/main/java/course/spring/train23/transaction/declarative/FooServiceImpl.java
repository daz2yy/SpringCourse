package course.spring.train23.transaction.declarative;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FooServiceImpl implements FooService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Override
    @Transactional
    public void insertRecord() {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('AAA')");
    }
    
    @Override
    @Transactional(rollbackFor = RollbackException.class)
    public void insertThenRollback() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('BBB')");
        throw new RollbackException();
    }
    
    @Override
    public void invokeInsertThenRollback() throws RollbackException {
        insertThenRollback();
    }
    
    // 第一种方法
    public void invokeInsert() throws RollbackException {
        FooService service = (FooService) AopContext.currentProxy();
        service.insertThenRollback();
    }
    
    // 第二种方法
    @Autowired
    FooService fooService;
    public void invokeInsert2() throws RollbackException {
        fooService.insertThenRollback();
    }
}
