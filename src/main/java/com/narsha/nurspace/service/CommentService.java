package com.narsha.nurspace.service;

import com.narsha.nurspace.dto.CommentEntity;
import com.narsha.nurspace.repository.CommentRepository;
import com.narsha.nurspace.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    /***
     * 전체 검색
     * @return 모든 CommentEntity 객체
     */
    public List<CommentEntity> findAll() {
        return commentRepository.findAll().stream()
                .map(comment -> new CommentEntity(comment.getComment_id(), comment.getContents()))
                .collect(Collectors.toList());
    }

    /***
     * id을 기준으로 댓글 검색하기
     * @param id 댓글 id
     * @return CommentEntity 객체
     */
    public CommentEntity findById(long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.map(value -> new CommentEntity(value.getComment_id(), value.getContents())).orElse(null);
    }

    /***
     * id을 기준으로 댓글 업데이트하기
     * @param id 댓글 id
     * @param request 변경할 내용이 담겨있는 CommentEntity 객체
     * @return 수정 완료된 CommentEntity 객체
     */
    public CommentEntity update(long id, CommentEntity request) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setContents(request.getContents());
            Comment updatedComment = commentRepository.save(comment);
            return new CommentEntity(updatedComment.getComment_id(), updatedComment.getContents());
        } else {
            return null;
        }
    }

    /***
     * id을 기준으로 댓글 삭제하기
     * @param id 댓글 id
     * @return 삭제 성공 여부
     */
    public boolean delete(long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
