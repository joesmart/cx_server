package com.server.cx.dto;

import com.google.common.base.Objects;

import java.util.List;

/**
 * User: ZouYanjian
 * Date: 12-6-29
 * Time: 下午5:52
 * FileName:GraphicCheckDTO
 */
public class GraphicCheckDTO {
    private String checkResult;
    private List<String> graphicIds;

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult.toUpperCase();
    }

    public List<String> getGraphicIds() {
        return graphicIds;
    }

    public void setGraphicIds(List<String> graphicIds) {
        this.graphicIds = graphicIds;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("checkResult", checkResult).add("List", graphicIds).toString();
    }
}
