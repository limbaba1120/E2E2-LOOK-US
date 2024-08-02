package org.example.post.controller;

import org.example.post.domain.dto.reqeust.PostCreateRequestDto;
import org.example.post.domain.dto.reqeust.PaginationRequestDto;
import org.example.post.domain.dto.response.PaginationResponseDto;
import org.example.post.domain.dto.response.PostResponseDto;
import org.example.post.repository.PostRepository;
import org.example.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
// @RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostApiController {

	private final PostRepository postRepository;
	private final PostService postService;
	//TODO: 로그인된 유저인지 확인하는 로직 필요, User가 아니여야 하는 거 아닌가...

	@PostMapping("/posts")
	public ResponseEntity<PostResponseDto> createPost(
		@Valid @RequestBody PostCreateRequestDto postCreateRequestDto) {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println("name: " + name);
		PostResponseDto postResponseDto = postService.createPost(postCreateRequestDto);
		return ResponseEntity.ok(postResponseDto);
	}
  
	// TODO: 전체 게시글 조회, 비로그인 유저
	// TODO: 게시글 검색, PostStatus 확인 조건 필요
	@GetMapping("/posts")
	public ResponseEntity<PaginationResponseDto> getAllPosts(
		@RequestParam(value = "searchHashtag", required = false) String searchHashtag,
		@RequestParam(value = "searchString", required = false) String searchString,
		@RequestParam(value = "sortField", defaultValue = "createdAt") String sortField,
		@RequestParam(value = "sortDirection", defaultValue = "DESC") String sortDirection,
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "size", defaultValue = "10") int size
	) {

		PaginationRequestDto paginationRequestDto = new PaginationRequestDto(page, size, sortField, sortDirection,
			searchHashtag, searchString
		);

		return postService.getAllPostsOrderedBySortStrategy(paginationRequestDto);
	}
}
