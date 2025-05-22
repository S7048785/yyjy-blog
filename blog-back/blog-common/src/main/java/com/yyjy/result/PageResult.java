package com.yyjy.result;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author YYJYP
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable {

	//总记录数
	private long total;

	//当前页数据集合
	private List<T> records;

	//当前页
	private long page;

	//每页记录数
	private long pageSize;
	
	public PageResult(long total, List<T> cardMessagePage, int page, int pageSize) {
		this.total = total;
		this.records = cardMessagePage;
		this.page = page;
		this.pageSize = pageSize;
	}

	public PageResult(Page<T> page) {
		super();
		this.total = page.getTotal();
		this.records = page.getRecords();
		this.page = page.getCurrent();
		this.pageSize = page.getSize();
	}
}