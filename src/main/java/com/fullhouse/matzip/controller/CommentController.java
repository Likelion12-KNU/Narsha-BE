package com.fullhouse.matzip.controller;

import com.fullhouse.matzip.dto.CommentEntity;
import com.fullhouse.matzip.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Comments")
@CrossOrigin(origins="*")
@Tag(name = "댓글 API Controller", description = "댓글 정보를 제공하는 메인 컨트롤러")
public class CommentController {
    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "상세 댓글")
    public ResponseEntity<CommentEntity> findCommentById(
            @Parameter(description = "검색할 댓글 ID", required = true)
            @PathVariable Long id
    ) {
        // 존재하지 않는 ID에 대한 예외 처리
        try {
            return new ResponseEntity<>(commentService.findById(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "댓글 변경")
    public ResponseEntity<CommentEntity> updateComment(
            @Parameter(description = "변경할 댓글 ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "댓글 변경 요청 객체", required = true)
            @RequestBody CommentEntity request
    ) {
        // 존재하지 않는 ID에 대한 예외 처리
        try {
            var updateComment = commentService.update(id, request);
            return new ResponseEntity<>(updateComment, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "삭제할 댓글 ID", required = true)
            @PathVariable Long id
    ) {
        // 존재하지 않는 ID에 대한 예외 처리
        try {
            commentService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
