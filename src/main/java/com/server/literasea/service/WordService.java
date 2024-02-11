package com.server.literasea.service;

import com.server.literasea.dto.WordInfoDto;
import com.server.literasea.entity.Users;
import com.server.literasea.entity.Word;
import com.server.literasea.exception.UsersException;
import com.server.literasea.exception.WordException;
import com.server.literasea.repository.UserRepository;
import com.server.literasea.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.server.literasea.exception.UsersExceptionType.NOT_FOUND;
import static com.server.literasea.exception.WordExceptionType.NOT_FOUND_ID;

@RequiredArgsConstructor
@Service
public class WordService {
    //repository------------------------------------------------------------------------
    private final WordRepository wordRepository;
    private final UserRepository userRepository;
    //공용------------------------------------------------------------------------------------
    //TODO: 토큰에서 유저 아이디 빼기
    private Long getUserIdByToken(String token){return 1l;};
    private Users findUsersById(Long usersId){
        return userRepository.findById(usersId)
                .orElseThrow(() -> new UsersException(NOT_FOUND));
    }
    //wordList--------------------------------------------------------------------
    @Transactional(readOnly = true)
    public List<WordInfoDto> getWordDtoList(Users users) {
        List<Word> wordList= findWordListByUsers(users);
        List<WordInfoDto> wordInfoDtoList=new ArrayList<>();
        for(Word word : wordList){
            wordInfoDtoList.add(WordInfoDto.from(word));
        }
        return wordInfoDtoList;
    }
    private List<Word> findWordListByUsers(Users users){
        return users.getWords();
    }
    //GETword------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public WordInfoDto findWordDtoByWordId(Long wordId){
        Word word= findWordByWordId(wordId);
        return WordInfoDto.from(word);
    }
    private Word findWordByWordId(Long wordId){
        return wordRepository.findById(wordId)
                .orElseThrow(()->new WordException(NOT_FOUND_ID));
    }
    //POSTword---------------------------------------------------------------------------------
    public Word saveWord(Long usersId, WordInfoDto wordInfoDto) {
        Users users=findUsersById(usersId);
        Word word=Word.from(wordInfoDto);
        users.addWord(word);
        wordRepository.save(word);
        return word;
    }
    //---------------------------------------------------------------------------------------



}
