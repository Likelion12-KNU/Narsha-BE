package com.fullhouse.matzip.controller;

import com.fullhouse.matzip.dto.CommentCreateRequest;
import com.fullhouse.matzip.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    @Operation(summary = "댓글 전체 조회")
    public ResponseEntity<List<CommentCreateRequest>> getAllComments() {
        List<CommentCreateRequest> comments = commentService.findAll();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "댓글 단일 조회")
    public ResponseEntity<CommentCreateRequest> getCommentById(
            @Parameter(description = "댓글 ID", required = true)
            @PathVariable long id
    ) {
        CommentCreateRequest comment = commentService.findById(id);
        if (comment != null) {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "댓글 수정")
    public ResponseEntity<CommentCreateRequest> updateComment(
            @Parameter(description = "댓글 ID", required = true)
            @PathVariable long id,
            @Parameter(description = "댓글 수정 요청 객체", required = true)
            @RequestBody CommentCreateRequest request
    ) {
        CommentCreateRequest updatedComment = commentService.update(id, request);
        if (updatedComment != null) {
            return new ResponseEntity<>(updatedComment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "댓글 ID", required = true)
            @PathVariable long id
    ) {
        boolean isDeleted = commentService.delete(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
