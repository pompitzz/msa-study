package me.sun.springbootstudy.domain.board;

public enum BoardType {

    NOTICE("공지사항"),
    FREE("자유게시판"),
    STUDY("공부방");

    private String value;

    BoardType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
