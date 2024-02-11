package com.server.literasea.service;

import com.server.literasea.dto.WordInfoDto;
import com.server.literasea.entity.Users;
import com.server.literasea.entity.Word;
import com.server.literasea.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WordService {
    private final WordRepository wordRepository;

    private List<Word> findWordListByUsers(Users users){
        return users.getWords();
    }

    private Word findWordByWordId(Long wordId) throws Exception {
        Optional<Word> optWord=wordRepository.findById(wordId);
        if(optWord.isPresent()) return optWord.get();
        throw new Exception();
    }

    public Word saveWord(WordInfoDto wordInfoDto) {
        Word word=Word.from(wordInfoDto);
        wordRepository.save(word);
        return word;
    }

    public List<WordInfoDto> getWordDtoList(Users users) {
        List<Word> wordList= findWordListByUsers(users);
        List<WordInfoDto> wordInfoDtoList=new ArrayList<>();
        for(Word word : wordList){
            wordInfoDtoList.add(WordInfoDto.from(word));
        }
        return wordInfoDtoList;
    }

    public WordInfoDto findWordDtoByWordId(Long wordId) throws Exception {
        Word word= findWordByWordId(wordId);
        return WordInfoDto.from(word);
    }
}
