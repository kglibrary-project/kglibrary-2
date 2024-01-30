package com.kg.library.Introduction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.kg.library.Introduction.BookDTO;
import com.kg.library.Introduction.BookService;
import com.kg.library.Introduction.IBookMapper;

import edu.emory.mathcs.backport.java.util.Collections;

@Service
public class Introduction_Service {
	
	@Autowired
	private IBookMapper mapper;
	 @Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = LoggerFactory.getLogger(BookService.class);
	
//	public List<Map<String, Object>> dataStatus(BookDTO board) {
//		
//		//String query = mapper.dataStatus(board);
//		String query = "SELECT category, COUNT(*) as bookCount, SUM(COUNT(*)) OVER() as totalBookCount FROM book GROUP BY category";
//		List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
//		//ArrayList<BookDTO> dataStatus = mapper.dataStatus(board);
//		
//		for (Map<String, Object> row : result) {
//			System.out.println("Category: " + row.get("category"));
//	        System.out.println("Book Count: " + row.get("bookCount"));
//	        System.out.println("Total Book Count: " + row.get("totalBookCount"));
//			}	
//		 return result;
//	}
	 
	 public List<Map<String, Object>> dataStatus(BookDTO board) {
			
		 try {
			 	ResponseEntity<List<Map<String, Object>>> responseEntity = new RestTemplate().exchange(
	                    "http://localhost:8087/dataStatus",
	                    HttpMethod.GET,
	                    null,
	                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
	            );

		 	  List<Map<String, Object>> dataStatus = responseEntity.getBody();
		 			        
		 	  for (Map<String, Object> data : dataStatus) {
		 		 System.out.println("Received data from the first project: " + data);
		            System.out.println("8087에서 얻어온 : Category : " + data.get("CATEGORY"));
		            System.out.println("8087에서 얻어온 : Count : " + data.get("BOOKCOUNT"));
		            System.out.println("8087에서 얻어온 : TOTALBOOKCOUNT : " + data.get("TOTALBOOKCOUNT"));
		        }
			 	 return dataStatus;
	            
	        } catch (HttpClientErrorException.NotFound notFoundException) {
	            // 404 Not Found 에러 처리
	            logger.error("Server returned 404 Not Found");
	            // 다른 예외에 대한 처리도 추가 가능
	            return Collections.emptyList(); // 빈 리스트 반환
	        } catch (Exception e) {
	            // 기타 예외 처리
	            logger.error("An error occurred while fetching data from the server", e);
	            return Collections.emptyList(); // 빈 리스트 반환
	        }
	 
		}
	}
	