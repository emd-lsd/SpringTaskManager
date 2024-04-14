package ru.webdev.springtaskmanager.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public Board createBoard(@RequestHeader("X-Board-Creator-Id") long userId,
                             @Valid @RequestBody Board board) {
        log.info("Creating board by user {}", userId);
        return boardService.createBoard(userId, board);
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@RequestHeader("X-Board-Creator-Id") long userId,
                            @PathVariable("boardId") long boardId) {
        log.info("Deleting board {} by user {}", boardId, userId);
        boardService.deleteBoard(userId, boardId);
    }

    @GetMapping("/{boardId}")
    public Board getBoard(@RequestHeader("X-Board-Creator-Id") long userId,
                          @PathVariable("boardId") long boardId) {
        log.info("Getting board {} by user {}", boardId, userId);
        return boardService.getBoardById(boardId);
    }
}
