package org.example.util.vo;

import lombok.Data;

/**
 * @author shenyichen
 * @date 2022/4/8
 **/
@Data
public class AnalysisVO {
    String hints;

    public AnalysisVO(String hints) {
        this.hints = hints;
    }
}
