package org.example.exception.post;

import org.example.exception.common.ApiErrorSubCategory;

public enum PostApiErrorSubCategory implements ApiErrorSubCategory {

	POST_NOT_FOUND("존재하지 않는 포스트입니다."),
	// ...
	;

	private final String userApiErrorSubCategory;

	PostApiErrorSubCategory(String userApiErrorSubCategory) {
		this.userApiErrorSubCategory = userApiErrorSubCategory;
	}

	@Override
	public String toString() {
		return String.format("[원인: %s]", this.userApiErrorSubCategory);
	}
}
