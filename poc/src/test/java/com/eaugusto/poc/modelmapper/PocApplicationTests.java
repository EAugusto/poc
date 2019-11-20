package com.eaugusto.poc.modelmapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.opentest4j.AssertionFailedError;
import org.springframework.boot.test.context.SpringBootTest;

import com.eaugusto.poc.modelmapper.model.Comment;
import com.eaugusto.poc.modelmapper.model.CommentIdDTO;
import com.eaugusto.poc.modelmapper.model.Post;

@SpringBootTest
class PocApplicationTests {
	
	@Test
	public void testConverterEqualFieldNames() {
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(Comment.class, CommentIdDTO.class);
		modelMapper.validate();
		
		Comment comment = new Comment();
		comment.setId(100);
		comment.setPostId(1000);
		comment.setName("Test Comment");
		
		CommentIdDTO commentIdDTO = modelMapper.map(comment, CommentIdDTO.class);
		
		assertEquals(100, commentIdDTO.getId());
		assertEquals(1000, commentIdDTO.getPostId());
		
	}
	
	@Test
	public void testConverterEqualFieldNamesReverseOrder() {
		ModelMapper modelMapper = new ModelMapper();
		
		CommentIdDTO commentIdDTO = new CommentIdDTO();
		commentIdDTO.setId(100);
		commentIdDTO.setPostId(1000);
		
		Comment comment = modelMapper.map(commentIdDTO, Comment.class);
		
		assertEquals(100, comment.getId());
		assertEquals(1000, comment.getPostId());
		assertNull(comment.getName());
	}
	
	/**
	 * Some similar fields may cause conversion problems
	 */
	@Test
	public void testExceptionOnDifferentTypeConversion() {
		
		ModelMapper modelMapper = new ModelMapper();
		
		CommentIdDTO commentIdDTO = new CommentIdDTO();
		commentIdDTO.setId(123);
		commentIdDTO.setPostId(10);
		
		Post post = modelMapper.map(commentIdDTO, Post.class);
		
		assertThrows(AssertionFailedError.class, () ->  assertEquals(10, post.getId()));
		assertNull(post.getTitle());
		assertNull(post.getBody());
	}

}
