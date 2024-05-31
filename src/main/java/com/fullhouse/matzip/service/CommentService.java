package com.fullhouse.matzip.service;

import com.fullhouse.matzip.dto.BoardEntityResponse;
import com.fullhouse.matzip.dto.CommentCreateRequest;
import com.fullhouse.matzip.dto.CommentListsResponse;
import com.fullhouse.matzip.model.Board;
import com.fullhouse.matzip.model.Comment;
import com.fullhouse.matzip.repository.BoardRepository;
import com.fullhouse.matzip.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(BoardRepository boardRepository, CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public CommentCreateRequest create(CommentCreateRequest request){
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        Comment saveComment = commentRepository.save(comment);

        return new CommentCreateRequest(saveComment.getComment_id(), saveComment.getContent());
    }

    public List<CommentCreateRequest> findAll() {
        return commentRepository.findAll().stream()
                .map(comment -> new CommentCreateRequest(comment.getComment_id(), comment.getContent()))
                .collect(Collectors.toList());
    }

    public CommentCreateRequest findById(long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return new CommentCreateRequest(comment.get().getComment_id(), comment.get().getContent());
        } else {
            return null;
        }
    }

    public CommentCreateRequest update(long id, CommentCreateRequest request) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setContent(request.getContent());
            Comment updatedComment = commentRepository.save(comment);
            return new CommentCreateRequest(updatedComment.getComment_id(), updatedComment.getContent());
        } else {
            return null;
        }
    }

    public boolean delete(long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
