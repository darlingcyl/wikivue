package com.frank.wiki.resp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
//@Data
    @Setter
    @Getter
public class EbookResp {

    private Long id;

    private String name;

    private Long category1Id;

    private Long category2Id;

    private String description;

    private String cover;

    private Integer docCount;

    private Integer viewCount;

    private Integer voteCount;
}
