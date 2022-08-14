package com.vinips.algafood.core.data;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageWrapper<T> extends PageImpl<T>{

	private static final long serialVersionUID = 1L;

	public PageWrapper(Page<T> page, Pageable pageable) {
		super(page.getContent(), page);
		//PAREI 8:25
		// TODO Auto-generated constructor stub
	}
	
	

}
