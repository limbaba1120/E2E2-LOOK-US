package org.example.post.repository;

import java.util.List;

import org.example.post.domain.entity.PostEntity;
import org.example.post.domain.enums.PostStatus;
import org.example.post.repository.custom.PostRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<PostEntity, Long>, PostRepositoryCustom {



	// @Query("SELECT DISTINCT p FROM PostEntity p JOIN p.hashtags h WHERE p.postContent LIKE %:postContent% AND h.hashtagContent IN :hashtags AND p.postStatus = :postStatus")
	Page<PostEntity> findAllByPostContentContainingAndHashtags_HashtagContentInAndPostStatus(
		@Param("postContent") String postContent,
		@Param("hashtags") List<String> hashtagList,
		@Param("postStatus") PostStatus postStatus,
		Pageable pageable
	);

}