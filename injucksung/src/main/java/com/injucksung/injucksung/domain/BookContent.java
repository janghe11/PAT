package com.injucksung.injucksung.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity @Table(name = "book_content")
@Setter @Getter
@NoArgsConstructor @RequiredArgsConstructor
@EqualsAndHashCode
public class BookContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Book book;

    @NonNull
    @Column(length = 100, nullable = false)
    private String name;

    @NonNull
    @Column
    private Long parentId;

    @NonNull
    @Column(nullable = false)
    private int sequence;

    @NonNull
    @Column(nullable = false)
    private Boolean isMockTest;

    @Column
    private Integer recommandTime; //권장시간 (단위 초)

    @Column(columnDefinition = "int default 0")
    private Integer questionCount;
}
