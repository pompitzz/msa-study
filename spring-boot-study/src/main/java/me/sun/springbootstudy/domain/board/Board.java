package me.sun.springbootstudy.domain.board;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sun.springbootstudy.domain.common.BaseTimeEntity;
import me.sun.springbootstudy.domain.member.Member;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private String author;

    private int viewsCount;

    private BoardType boardType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Board(String title, String content, String author, int viewsCount, BoardType boardType, Member member) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.viewsCount = viewsCount;
        this.boardType = boardType;
        this.member = member;
    }

    public void update(String title, String content, BoardType boardType) {
        this.title = title;
        this.content = content;
        this.boardType = boardType;
    }


}
