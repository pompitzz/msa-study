package me.sun.springbootstudy;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
class AppRunnerWithLocalTest {

//    @Autowired
//    BoardRepository boardRepository;
//
//    @Test
//    @DisplayName("profile이 local일땐 Board가 30이다.")
//    void boardSize30() throws Exception{
//        List<Board> boards = boardRepository.findAll();
//        assertThat(boards.size()).isEqualTo(30);
//    }
}