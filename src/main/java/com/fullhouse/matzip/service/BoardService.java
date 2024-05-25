package com.fullhouse.matzip.service;

import com.fullhouse.matzip.dto.BoardCreateRequest;
import com.fullhouse.matzip.dto.BoardListsResponse;
import com.fullhouse.matzip.dto.BoardListEntityResponse;
import com.fullhouse.matzip.dto.BoardUpdateRequest;
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
     * 페이지 단위로 게시판 리스트 리턴
     *
     * @param howMany 한 페이지에 들어가는 게시판의 수
     * @param pageNum 페이지번호
     * @return BoardList List
     */
    public BoardListsResponse findPart(Integer howMany, Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum, howMany);
        Page<Board> page = boardRepository.findAll(pageable);

        List<BoardListEntityResponse> boardLists = page.getContent().stream()
                .map(board -> new BoardListEntityResponse(board.getTitle(), board.getLikes(), board.getEditDt()))
                .collect(Collectors.toList());

        return new BoardListsResponse(boardLists, page.getTotalPages());
    }

    /**
     * 요청을 기반으로 새로운 게시판을 생성
     * @param request 게시판 생성 요청 객체
     * @return 생성된 게시판의 제목과 내용을 포함하는 BoardCreateRequest 객체
     */
    public BoardCreateRequest createBoard(BoardCreateRequest request){
        // 새로운 model 생성
        Board board = new Board();
        board.setTitle(request.getTitle());
        board.setContents(request.getContents());
        Board savedBoard = boardRepository.save(board);

        // 리턴을 위한 dto 생성 및 리턴
        return new BoardCreateRequest(savedBoard.getTitle(), savedBoard.getContents());
    }

    /**
     * 주어진 ID를 가진 게시판을 수정
     * @param id 수정할 게시판 ID
     * @param request 게시판 수정 요청 객체
     * @return 생성된 게시판의 요소를 포함하는 BoardUpdateRequest 객체
     */
    public BoardUpdateRequest updateBoard(Long id, BoardUpdateRequest request) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        board.setTitle(request.getTitle());
        board.setContents(request.getContents());

        Board savedBoard = boardRepository.save(board);
        return new BoardUpdateRequest(savedBoard.getTitle(), savedBoard.getContents(), savedBoard.getLikes());
    }

    /**
     * 주어진 ID를 가진 게시판을 삭제
     * @param id 삭제할 게시판 ID
     */
    public void deleteBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        boardRepository.delete(board);
    }
}
