package com.example.toyspring;

import com.example.toyspring.prepared.ConnectDB;
import com.example.toyspring.prepared.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
public class PreparedTest {

    @Autowired
    private ConnectDB connectDB;

    private Set<String> groupList = Set.of("Aespa", "NewJeans");

    @Test
    @DisplayName("JDBC Driver")
    void db_connenct_only_jdbc_driver()  {

        groupList.stream().forEach(group -> {
            List<UserDto> userDtoList = connectDB.userListByNameGroup(group);
            userDtoList.stream().forEach(System.out::println);
            assertThat(userDtoList.size(), is(notNullValue()));
            assertThat(CollectionUtils.isEmpty(userDtoList), is(false));
        });
    }

}
