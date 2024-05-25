package com.fullhouse.matzip.service;

import com.fullhouse.matzip.dto.BoardListDTO;
import com.fullhouse.matzip.dto.BoardListRequestDTO;
import com.fullhouse.matzip.model.Board;
import com.fullhouse.matzip.repository.BoardRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    /**
     * 페이지 단위로 운동기록을 리턴
     *
     * @param howMany 한 페이지에 들어가는 운동기록의 수
     * @param pageNum 페이지번호
     * @return BoardList List
     */
    public BoardListRequestDTO findPart(Integer howMany, Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum, howMany);
        Page<Board> page = boardRepository.findAll(pageable);

        List<BoardListDTO> boardLists = page.getContent().stream()
                .map(board -> new BoardListDTO(board.getTitle(), board.getLikes(), board.getEditDt()))
                .collect(Collectors.toList());

        return new BoardListRequestDTO(boardLists, page.getTotalPages());
    }

    public Board createBoard(Board board){
        return boardRepository.save(board);
    }

    public void deleteBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        boardRepository.delete(board);
    }
}
