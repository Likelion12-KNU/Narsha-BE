package com.fullhouse.matzip.controller;

import com.fullhouse.matzip.dto.BoardCreateRequest;
import com.fullhouse.matzip.dto.BoardListsResponse;
import com.fullhouse.matzip.dto.BoardUpdateRequest;
import com.fullhouse.matzip.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@CrossOrigin(origins="*")
@Tag(name = "게시판 API Controller", description = "게시판 정보를 제공하는 메인 컨트롤러")
public class BoardListController {
    private final BoardService boardService;
    public BoardListController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    @Operation(summary = "게시판 생성")
    public ResponseEntity<BoardCreateRequest> createBoard(
            @Parameter(description = "게시판 생성 요청 객체", required = true)
            @RequestBody BoardCreateRequest request
    ){
        // 요청 객체 유효성 검사
        if (request == null || request.getTitle() == null || request.getContents() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoardCreateRequest response = boardService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/lists")
    @Operation(summary = "게시판 목록")
    public ResponseEntity<BoardListsResponse> findBoardPart(
            @Parameter(description = "화면에 표기할 항목 수", required = false)
            @RequestParam(value="howMany") Integer howMany,

            @Parameter(description = "요청할 페이지 번호", required = true)
            @RequestParam(value="pageNum") Integer pageNum
    ) {
        // 값의 범위 검사 추가
        if (howMany <= 0 || pageNum <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(boardService.findPart(howMany, pageNum), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "게시판 변경")
    public ResponseEntity<Void> updateBoard(
            @Parameter(description = "변경할 게시판 ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "게시판 변경 요청 객체", required = true)
            @RequestBody BoardUpdateRequest request
    ) {
        // 존재하지 않는 ID에 대한 예외 처리
        try {
            boardService.update(id, request);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "게시판 삭제")
    public ResponseEntity<Void> deleteBoard(
            @Parameter(description = "삭제할 게시판 ID", required = true)
            @PathVariable Long id
    ) {
        // 존재하지 않는 ID에 대한 예외 처리
        try {
            boardService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
