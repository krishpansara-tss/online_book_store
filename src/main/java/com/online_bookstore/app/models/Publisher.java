package com.online_bookstore.app.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Data
@Entity
@Table(name ="publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publisherId;
    private String name;

    @OneToMany(mappedBy = "publisher")
    private List<Book> books = new ArrayList<>();
}
