package com.fullhouse.matzip.controller;

import com.fullhouse.matzip.dto.BoardListRequestDTO;
import com.fullhouse.matzip.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@CrossOrigin(origins="*")
@Tag(name = "게시판 API Controller", description = "게시판 정보를 제공하는 메인 컨트롤러")
public class BoardListContoller {

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody Board board){
        Board savedBoard = boardService.createBoard(board);
        return ResponseEntity.ok(savedBoard);
    }

    private final BoardService boardService;
    public BoardListContoller(BoardService boardService) {
        this.boardService = boardService;
    }

    @Operation(summary = "게시판 목록")
    @GetMapping("/lists")
    public ResponseEntity<BoardListRequestDTO> findPart(
            @Parameter(description = "화면에 표기할 항목 수", required = true)
            @RequestParam(value="howMany") Integer howMany,

            @Parameter(description = "요청할 페이지 번호", required = true)
            @RequestParam(value="pageNum") Integer pageNum
    ){
        return new ResponseEntity<>(boardService.findPart(howMany, pageNum), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id){
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}
