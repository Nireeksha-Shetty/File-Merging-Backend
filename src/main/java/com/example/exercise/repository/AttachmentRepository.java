package com.example.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.exercise.entity.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, String> {
}
