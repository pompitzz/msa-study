package me.sun.springbootstudy.domain.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sun.springbootstudy.domain.board.Board;
import me.sun.springbootstudy.domain.common.BaseTimeEntity;
import me.sun.springbootstudy.domain.member.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JsonIgnore
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = LAZY)
    @JsonIgnore
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> child = new ArrayList<>();

    private String content;

    private Integer depth;

    // 생성메서드 //
    public static Comment createComment(Member member, Board board, Comment parent, String conent) {
        Comment comment = new Comment();

        comment.setBoard(board);
        comment.setMember(member);
        comment.content = conent;

        if (parent == null) {
            comment.depth = 0;
        } else {
            comment.depth = parent.depth + 1;
            comment.setParent(parent);
        }

        return comment;
    }

    // 연관관계 편의 메서드 //
    public void setParent(Comment parent) {
        this.parent = parent;
        parent.getChild().add(this);
    }

    public void setBoard(Board board) {
        this.board = board;
        board.getComments().add(this);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getComments().add(this);
    }

    public void update(String content) {
        this.content = content;
    }

}
