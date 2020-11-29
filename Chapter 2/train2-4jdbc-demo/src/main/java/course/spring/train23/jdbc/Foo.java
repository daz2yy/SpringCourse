package course.spring.train23.jdbc;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Foo {
    private int id;
    private String name;
}
