package com.narsha.nurspace.service;

import com.narsha.nurspace.repository.BoardRepository;
import com.narsha.nurspace.dto.*;
import com.narsha.nurspace.model.Board;
import com.narsha.nurspace.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
     * 요청을 기반으로 새로운 게시판을 생성
     *
     * @param request 게시판 생성 요청 객체
     * @return 생성된 게시판의 제목과 내용을 포함하는 BoardCreateRequest 객체
     */
    public BoardCreateRequest create(BoardCreateRequest request) {
        // 새로운 model 생성
        Board board = new Board();
        board.setTitle(request.getTitle());
        board.setContents(request.getContents());
        board.setLatitude(request.getCoordinate().getLatitude());
        board.setLongitude(request.getCoordinate().getLongitude());
        Board savedBoard = boardRepository.save(board);

        // 리턴을 위한 dto 생성 및 리턴
        return new BoardCreateRequest(savedBoard.getTitle(), savedBoard.getContents(), new Coordinate(savedBoard.getLatitude(), savedBoard.getLongitude()), savedBoard.getStoreName());
    }

    /**
     * ID을 기준으로 좋아요 수 업데이트
     *
     * @param id 검색할 ID
     * @return 검색한 BoardEntityResponse 객체
     */
    public BoardEntityResponse addLikeById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        board.addLikes();
        Board savedBoard = boardRepository.save(board);

        List<CommentEntity> commentEntities = board.getComments().stream()
                .map(comment -> new CommentEntity(
                        comment.getComment_id(),
                        comment.getContents()
                )).collect(Collectors.toList());
        Coordinate coordinate = new Coordinate(savedBoard.getLatitude(), savedBoard.getLongitude());
        return new BoardEntityResponse(savedBoard.getId(), savedBoard.getTitle(), savedBoard.getContents(), savedBoard.getLikes(), savedBoard.getEditDt(), commentEntities, coordinate, savedBoard.getStoreName());
    }

    /***
     * boardId을 기준으로 Comment 추가
     * @param boardId 게시판 ID
     * @param comment 댓글
     * @return 추가한 BoardEntityResponse 객체
     */
    public BoardEntityResponse addCommentToBoard(long boardId, CommentEntity comment) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("Post not found with id " + boardId));

        Comment newComment = new Comment();
        newComment.setContents(comment.getContents());
        newComment.setBoard(board);

        board.addComment(newComment);
        Board savedBoard = boardRepository.save(board);

        List<CommentEntity> commentEntities = savedBoard.getComments().stream()
                .map(comment_ -> new CommentEntity(
                        comment_.getComment_id(),
                        comment_.getContents()
                )).collect(Collectors.toList());
        Coordinate coordinate = new Coordinate(savedBoard.getLatitude(), savedBoard.getLongitude());
        return new BoardEntityResponse(savedBoard.getId(), savedBoard.getTitle(), savedBoard.getContents(), savedBoard.getLikes(), savedBoard.getEditDt(), commentEntities, coordinate, savedBoard.getStoreName());
    }

    /**
     * ID을 기준으로 게시판을 검색하여 리턴
     *
     * @param id 검색할 ID
     * @return 검색한 BoardEntityResponse 객체
     */
    public BoardEntityResponse findById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id " + id));

        List<CommentEntity> commentEntities = board.getComments().stream()
                .map(comment -> new CommentEntity(
                        comment.getComment_id(),
                        comment.getContents()
                )).collect(Collectors.toList());
        Coordinate coordinate = new Coordinate(board.getLatitude(), board.getLongitude());
        return new BoardEntityResponse(board.getId(), board.getTitle(), board.getContents(), board.getLikes(), board.getEditDt(), commentEntities, coordinate, board.getStoreName());
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

        List<BoardEntityResponse> boardLists = page.getContent().stream()
                .map(board -> new BoardEntityResponse(
                        board.getId(),
                        board.getTitle(),
                        board.getContents(),
                        board.getLikes(),
                        board.getEditDt(),
                        board.getComments().stream()
                                .map(comments -> new CommentEntity(comments.getComment_id(), comments.getContents()))
                                .collect(Collectors.toList()),
                        new Coordinate(board.getLatitude(), board.getLongitude()
                        ),
                        board.getStoreName()))
                .collect(Collectors.toList());
        return new BoardListsResponse(boardLists, page.getTotalPages());
    }

    /**
     * 주어진 ID를 가진 게시판을 수정
     *
     * @param id      수정할 게시판 ID
     * @param request 게시판 수정 요청 객체
     * @return 생성된 게시판의 요소를 포함하는 BoardUpdateRequest 객체
     */
    public BoardUpdateRequest update(Long id, BoardUpdateRequest request) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        board.setTitle(request.getTitle());
        board.setContents(request.getContents());

        Board savedBoard = boardRepository.save(board);
        return new BoardUpdateRequest(savedBoard.getTitle(), savedBoard.getContents());

    }

    /**
     * 주어진 ID를 가진 게시판의 태그를 업데이트
     *
     * @param id  태그를 업데이트할 게시판 ID
     * @param tag 업데이트할 태그
     * @return 업데이트된 태그를 포함하는 BoardEntityResponse 객체
     */
    public BoardEntityResponse updateTag(Long id, String tag) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        board.setTag(tag);

        Board savedBoard = boardRepository.save(board);
        List<CommentEntity> commentEntities = board.getComments().stream()
                .map(comment -> new CommentEntity(
                        comment.getComment_id(),
                        comment.getContents()
                )).toList();
        Coordinate coordinate = new Coordinate(board.getLatitude(), board.getLongitude());
        return new BoardEntityResponse(savedBoard.getId(), savedBoard.getTitle(), savedBoard.getContents(), savedBoard.getLikes(), savedBoard.getEditDt(), commentEntities, coordinate, savedBoard.getStoreName());
    }

    /**
     * 게시판에 좌표를 추가하거나 업데이트합니다.
     *
     * @param id         좌표를 추가 또는 업데이트할 게시판의 ID
     * @param coordinate 추가 또는 업데이트할 좌표 정보
     * @return 업데이트된 좌표를 포함하는 Coordinate 객체
     * @throws RuntimeException 게시판을 찾을 수 없는 경우 예외 발생
     */
    public Coordinate updateCoordinate(Long id, Coordinate coordinate) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id" + id));
        board.setLatitude(coordinate.getLongitude());
        board.setLongitude(coordinate.getLongitude());

        Board saveBoard = boardRepository.save(board);
        return new Coordinate(saveBoard.getLatitude(), saveBoard.getLongitude());
    }

    /**
     * 주어진 ID를 가진 게시판을 삭제
     *
     * @param id 삭제할 게시판 ID
     */
    public void delete(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        boardRepository.delete(board);
    }

}
