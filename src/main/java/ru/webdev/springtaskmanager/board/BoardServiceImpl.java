package ru.webdev.springtaskmanager.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdev.springtaskmanager.exception.NotFoundDataException;
import ru.webdev.springtaskmanager.user.Role;
import ru.webdev.springtaskmanager.user.User;
import ru.webdev.springtaskmanager.user.UserRepository;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public Board createBoard(long userId, Board board) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundDataException("User with id: " + userId + " not found"));
        user.setRole(Role.ADMIN);
        board.setCreator(user);
        return boardRepository.save(board);
    }

    @Override
    public void deleteBoard(long userId, long boardId) {
        if (userRepository.findById(userId).isPresent()) {
            if (boardRepository.findById(boardId).isPresent()) {
                boardRepository.deleteById(boardId);
            } else throw new NotFoundDataException("Board with id: " + boardId + " not found");
        } else throw new NotFoundDataException("User with id: " + userId + " not found");
    }

    @Override
    public Board getBoardById(long boardId) {
        if (userRepository.findById(boardId).isPresent()) {
            if (boardRepository.findById(boardId).isPresent()) {
                return boardRepository.findById(boardId).get();
            } else throw new NotFoundDataException("Board with id: " + boardId + " not found");
        } else throw new NotFoundDataException("User with id: " + boardId + " not found");
    }
}
