package com.qihang.winter.winterdao.pojo;

import java.util.List;

/**
 * @author Zerrion
 * @version V1.1
 * @Title:WinterDaoPage
 * @description:Winterdao自动分页设置
 * @date 2014-3-12 下午10:22:24
 */
public class WinterDaoPage<T> {
  // 当前页面
  private int page;
  // 每页显示记录数
  private int rows;
  // 总行数
  private int total;
  // 总页数
  private int pages;
  // 结果集
  private List<T> results;

  public int getPage() {
    return page;
  }

  public int getPages() {
    return pages;
  }

  public List<T> getResults() {
    return results;
  }

  public int getRows() {
    return rows;
  }

  public int getTotal() {
    return total;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  public void setResults(List<T> results) {
    this.results = results;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public void setTotal(int total) {
    this.total = total;
    this.pages = total / rows + (total % rows > 0 ? 1 : 0);
  }
}
