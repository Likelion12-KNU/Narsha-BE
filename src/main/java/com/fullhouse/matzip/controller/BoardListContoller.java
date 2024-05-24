package com.fullhouse.matzip.controller;

import com.fullhouse.matzip.dto.BoardListRequestDTO;
import com.fullhouse.matzip.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/BoardLists")
@CrossOrigin(origins="*")
public class BoardListContoller {

    private final BoardService boardService;
    public BoardListContoller(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/page")
    public ResponseEntity<BoardListRequestDTO> findPart(
            @RequestParam(value="howMany") Integer howMany,
            @RequestParam(value="pageNum") Integer pageNum){
        return new ResponseEntity<>(boardService.findPart(howMany, pageNum), HttpStatus.OK);
    }
}
