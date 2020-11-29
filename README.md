# 第一章 Hello World!

1. 实现一个 SpringBoot 的接口后台，提供以下功能：

   - 访问 http://localhost:60038/hello 返回 Hello World!

   - 要点：

     - 在 https://start.spring.io/ 生成基础代码，要加上 **Lombok**、**Spring Web** 依赖，不然用不了相关注解

     - 修改 **src/main/resources/application.properties** ：

       ```
       server.port=60038
       ```

     - 注解理解：@RestController，@RequestMapping("hello")

# 第二章 数据库

1. 配置一个数据源，并连接获取一个表的信息，打印出来：

   - 通过自动加载的方式配置数据源

     - 在 stater.spring.io 选择依赖：H2， JDBC，Web，Actuator，Lombok
     - 不做特殊配置，直接运行，打印出连接相关信息。
     - 总结：
       1. CommandLineRunner 接口的使用
       2. @Autowried 类型：DataSource，JdbcTemplate
       3. 懒加载：dataSource.getConnection()

   - 不用 SpringBoot 的方式加载，需要自己写 dependency

     - 通过xml 的方式配置数据源

       ```
       <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
       	<property name="driverClassName" value="org.h2.Driver"/>
       	<property name="url" value="jdbc:h2:mem:testdb" />
       	<property name="username" value="SA" />
       	<property name="password" value="" />
       </bean>
       ```

     - 通过 @Bean 注释加载数据源

   - 通过 actuator/beans 查看 SQL 相关的 Bean

2. 配置多数据源
   - 使用 @Primary 来配置
   - 不适用 @Primary 来配置，去掉自动加载类
3. 使用阿里巴巴的 druid 数据库连接池，并添加一个自定义 Filter，在连接池连接之前和之后打印信息。
4. 使用 JDBC 实现插入数据，批量插入数据，查询插入的数据。
   - 使用 SimpleJdbcInsert 实现插入
   - 使用 NamedParameterJdbcTemplate 实现插入