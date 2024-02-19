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
        List<Word> wordList=wordRepository.findAllByUsers(users);
        List<WordInfoDto> wordInfoDtoList=new ArrayList<>();
        for(int i=0;i<wordList.size();i++){
            wordInfoDtoList.add(WordInfoDto.from(wordList.get(i)));
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
    @Transactional
    public WordInfoDto saveWord(Users logInUser, WordInfoDto wordInfoDto) {
        Users user=findUsersById(logInUser.getId());  //이거 왜 다이렉트로 매개변수로 받은 User쓰면 안됨?
        Word word=Word.from(wordInfoDto);
        user.addWord(word);
        wordRepository.save(word);
        return wordInfoDto;  //이거 Word로 리턴하면 왜 오류?
    }
    //---------------------------------------------------------------------------------------



}
