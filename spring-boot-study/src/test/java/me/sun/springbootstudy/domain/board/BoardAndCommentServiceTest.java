package me.sun.springbootstudy.domain.board;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//TODO 테스트 작성하자
class BoardAndCommentServiceTest {

    @Test
    public void listTest() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> subList = list.subList(0, 2);

        assertThat(subList.size()).isEqualTo(2);
        assertThat(subList.get(0)).isEqualTo(1);
        assertThat(subList.get(1)).isEqualTo(2);
    }

}