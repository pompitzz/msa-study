package me.sun.springbootstudy;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("real1")
class AppRunnerWithReal1Test {
//
//    @Autowired
//    BoardRepository boardRepository;
//
//    @Test
//    @DisplayName("profile이 real1일땐 Board가 0이다.")
//    void boardSize0() throws Exception{
//        List<Board> boards = boardRepository.findAll();
//        assertThat(boards.size()).isEqualTo(0);
//    }

}