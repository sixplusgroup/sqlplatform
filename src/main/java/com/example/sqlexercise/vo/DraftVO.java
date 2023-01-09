package com.example.sqlexercise.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DraftVO {
    private String userId;
    private Integer mainId;
    private Integer subId;
    private String draft;   // 要保存的草稿
    private Date saveTime;  // 草稿保存时间
}
