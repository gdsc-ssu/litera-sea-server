package com.server.literasea.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ResponseDictDto {
    private Channel channel;

    @Getter
    @Setter
    public static class Channel {
        private int total;
        private int num;
        private String title;
        private int start;
        private String description;
        private List<Item> item;
    }

    @Getter
    @Setter
    public static class Item {
        private String sup_no;
        private String word;
        private Sense sense;
        private String pos;
    }

    @Getter
    @Setter
    public static class Sense {
        private String definition;
        private String link;
        private String type;
    }
}
