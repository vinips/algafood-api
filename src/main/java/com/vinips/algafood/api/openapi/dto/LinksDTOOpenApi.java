package com.vinips.algafood.api.openapi.dto;

import io.swagger.annotations.ApiModel;

@ApiModel("Links")
public class LinksDTOOpenApi {

	private LinkDTO rel;
	
	@ApiModel("Link")
	protected class LinkDTO {
		
		private String href;
		
		private boolean templated;

		public String getHref() {
			return href;
		}

		public void setHref(String href) {
			this.href = href;
		}

		public boolean isTemplated() {
			return templated;
		}

		public void setTemplated(boolean templated) {
			this.templated = templated;
		}
		
	}

	public LinkDTO getRel() {
		return rel;
	}

	public void setRel(LinkDTO rel) {
		this.rel = rel;
	}
	
}
