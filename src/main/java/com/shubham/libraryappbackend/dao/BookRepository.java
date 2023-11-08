package com.shubham.libraryappbackend.dao;

import com.shubham.libraryappbackend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
