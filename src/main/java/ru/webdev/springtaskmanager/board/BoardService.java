package ru.webdev.springtaskmanager.board;

public interface BoardService {
    Board createBoard(long userId, Board board);

    void deleteBoard(long userId, long boardId);

    Board getBoardById(long boardId);
}
