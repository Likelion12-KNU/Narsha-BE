package com.fullhouse.matzip.controller;

import com.fullhouse.matzip.dto.BoardCreateRequest;
import com.fullhouse.matzip.dto.CommentCreateRequest;
import com.fullhouse.matzip.model.Comment;
import com.fullhouse.matzip.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins="*")
@Tag(name = "댓글 API Controller", description = "댓글 정보를 제공하는 메인 컨트롤러")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){this.commentService = commentService;}

    @PostMapping
    @Operation(summary = "댓글 기능 추가")
    public ResponseEntity<CommentCreateRequest> createComment(
            @Parameter(description = "댓글 생성 요청 객체", required = true)
            @RequestBody CommentCreateRequest request
            )
    {
        if (request == null || request.getComment_id() == null || request.getContent() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CommentCreateRequest response = commentService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
