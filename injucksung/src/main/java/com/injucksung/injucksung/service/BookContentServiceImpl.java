package com.injucksung.injucksung.service;

import com.injucksung.injucksung.domain.BookContent;
import com.injucksung.injucksung.dto.BookContentForm;
import com.injucksung.injucksung.repository.BookContentRepository;
import com.injucksung.injucksung.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookContentServiceImpl implements BookContentService {
    private final BookContentRepository bookContentRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public BookContent addBookContent(BookContentForm bookContentForm) {
        BookContent bookContent = convertFormToBookContent(bookContentForm);
        return bookContentRepository.save(bookContent);
    }

    private BookContent convertFormToBookContent(BookContentForm bookContentForm) {
        //depth 설정
        Integer depth = bookContentForm.getDepth();
        bookContentForm.setDepth((depth == null) ? 0 : depth + 1);

        //questionCount 0으로 설정
        bookContentForm.setQuestionCount(0);

        //sequence 설정
        int maxSequenceByBookIdAndDepth =
                bookContentRepository.findMaxSequenceByBookIdAndDepth(bookContentForm.getBookId(), bookContentForm.getDepth());
        bookContentForm.setSequence(maxSequenceByBookIdAndDepth + 1);

        BookContent bookContent = new BookContent();
        BeanUtils.copyProperties(bookContentForm, bookContent);

        bookContent.setBook(bookRepository.findBookById(bookContentForm.getBookId()));
        return bookContent;
    }

    @Override
    @Transactional
    public void deleteBookContent(Long id) {
        arrangeSequencePull(id);
        bookContentRepository.deleteById(id);
    }

    //삭제 시에 sequence (정렬 순서) 재정렬
    private void arrangeSequencePull(Long id) {
        BookContent bookContentById = bookContentRepository.findBookContentById(id);
        Integer sequence = bookContentById.getSequence();
        Long superBookContentId = null;
        try {
            superBookContentId = bookContentById.getSuperBookContent().getId();
        } catch (NullPointerException e) {
            e.printStackTrace();
            //TODO 대분류 삭제시에 별개로 처리하기 
            return;
        }
        bookContentRepository.arrangeSequencePull(superBookContentId, sequence);
    }

    @Override
    @Transactional
    public int modifyBookContent(BookContent bookContent) {
//        BookContent modifyBookContent = bookContentRepository.save(bookContent);
//        if (modifyBookContent != null) {
//            bookContentRepository.flush();
//            return 1;
//        }
//
        return 0;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookContent> getBookContentList(Long bookId) {
        return sortBookContents(bookContentRepository.findBookContentByBookId(bookId));
    }

    private List<BookContent> sortBookContents(List<BookContent> bookContentList) {
        //TODO 책 목차 정렬하는 알고리즘 고민중 (현재 정렬 잘 안됨)
        for (int i = 0; i < bookContentList.size(); i++) {
            BookContent bookContent = bookContentList.get(i);
            BookContent superBookContent = bookContent.getSuperBookContent();
            if (superBookContent != null) {
                Collections.swap(bookContentList,
                        i, bookContentList.indexOf(superBookContent) + 1 + bookContent.getSequence());
            }
        }

        return bookContentList;
    }

    @Override
    @Transactional(readOnly = true)
    public BookContent getBookContent(Long bookContentId) {
        return bookContentRepository.findBookContentById(bookContentId);
    }
}
