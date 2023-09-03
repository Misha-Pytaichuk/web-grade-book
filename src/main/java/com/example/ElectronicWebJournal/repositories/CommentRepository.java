package com.example.ElectronicWebJournal.repositories;

import com.example.ElectronicWebJournal.models.Comment;
import com.example.ElectronicWebJournal.models.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByGrade(Grade grade);
    
}
