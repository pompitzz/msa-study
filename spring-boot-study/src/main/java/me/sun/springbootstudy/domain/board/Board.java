package me.sun.springbootstudy.domain.board;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sun.springbootstudy.domain.comment.Comment;
import me.sun.springbootstudy.domain.common.BaseTimeEntity;
import me.sun.springbootstudy.domain.member.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private int viewsCount;

    private int commentCount;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Board(String title, String content, int viewsCount, BoardType boardType, Member member, int commentCount) {
        this.title = title;
        this.content = content;
        this.viewsCount = viewsCount;
        this.boardType = boardType;
        this.member = member;
        this.commentCount = commentCount;
    }

    public void update(String title, String content, BoardType boardType) {
        this.title = title;
        this.content = content;
        this.boardType = boardType;
    }

    public void countOneComment() {
        this.commentCount++;
    }
}
