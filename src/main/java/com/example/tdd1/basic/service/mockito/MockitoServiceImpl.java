package com.example.tdd1.basic.service.mockito;

import com.example.tdd1.basic.domain.User;
import com.example.tdd1.basic.dto.MockitoDto;
import com.example.tdd1.basic.dto.UserDto;
import com.example.tdd1.basic.mybatis.TestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class MockitoServiceImpl implements MockitoService {

    private final TestMapper testMapper;

    public MockitoServiceImpl(TestMapper testMapper) {
        this.testMapper = testMapper;
    }
//    private final MockitoRepository mockitoRepository;
//
//    public MockitoServiceImpl(MockitoRepository mockitoRepository) {
//        this.mockitoRepository = mockitoRepository;
//    }
    @Override
    public UserDto selectUser(String userId) {
        return this.testMapper.selectUser(userId);
    }

    @Override
    public boolean updateUser(UserDto dto) {
        return this.testMapper.updateUser(dto) > 0;
    }
    @Override
    public List<User> getUsers(MockitoDto dto) {
        return null;
    }

    @Override
    public List<MockitoDto> getDataList(MockitoDto dto) {
        log.info("============= MockitoServiceImpl getDataList called!");
        return Collections.emptyList();
//        return mockitoRepository.getDataList(dto);
    }

    @Override
    public int calculateValue(int a, int b) {
        return 0;
    }

    @Override
    public String getTeamName(String team) {
        System.out.println(">>>>>>> getTeamName get call");
        return team + " Team";
    }
}
