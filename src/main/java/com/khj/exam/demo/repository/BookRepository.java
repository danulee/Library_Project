package com.khj.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.khj.exam.demo.vo.Book;

@Mapper
public interface BookRepository {
//	public void writeArticle(@Param("memberId") int memberId, @Param("boardId") int boardId,
//			@Param("title") String title, @Param("body") String body);

	@Select("""
			<script>
			SELECT B.*
			FROM book as B
			WHERE 1
			AND B.id = #{id}
			</script>
			""")
	public Book getForPrintBook(@Param("id") int id);

//	public void deleteArticle(@Param("id") int id);
//
//	public void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

	@Select("""
			<script>
				SELECT *
				FROM book AS B
				WHERE 1
				<if test="searchKeyword != ''">
					<choose>
						<when test="searchKeywordTypeCode == 'title'">
							AND B.title LIKE CONCAT('%', #{searchKeyword}, '%')
						</when>
						<when test="searchKeywordTypeCode == 'writer'">
							AND B.writer LIKE CONCAT('%', #{searchKeyword}, '%')
						</when>
						<otherwise>
							AND (
								B.title LIKE CONCAT('%', #{searchKeyword}, '%')
								OR
								B.writer LIKE CONCAT('%', #{searchKeyword}, '%')
							)
						</otherwise>
					</choose>
				</if>
				ORDER BY B.id DESC
				<if test="limitTake != -1">
					LIMIT #{limitStart}, #{limitTake}
				</if>
			</script>
			""")
	public List<Book> getForPrintBooks(String searchKeyword, String searchKeywordTypeCode, int limitStart,
			int limitTake);

	public int getLastInsertId();

	@Select("""
			<script>
			SELECT COUNT(*) AS cnt
			FROM book AS B
			WHERE 1
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'title'">
						AND B.title LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordTypeCode == 'writer'">
						AND B.writer LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<otherwise>
						AND (
							B.title LIKE CONCAT('%', #{searchKeyword}, '%')
							OR
							B.writer LIKE CONCAT('%', #{searchKeyword}, '%')
						)
					</otherwise>
				</choose>
			</if>
			</script>
			""")
	public int getBooksCount(String searchKeywordTypeCode, String searchKeyword);

	@Update("""
			<script>
			UPDATE book
			SET rentalDate =NOW(), rental =1
			WHERE id = #{id}
			</script>
			""")
	public int rentalBook(int id);

	@Update("""
			<script>
			UPDATE book
			SET returnDate = (SELECT DATE_ADD(rentalDate, INTERVAL 7 DAY) FROM book WHERE id = #{id})
			WHERE id = #{id}
			</script>
			""")
	public void returnDate(int id);

	@Update("""
			<script>
			UPDATE book
			SET rentalDate = null , returnDate = null , rental = 0
			WHERE id = #{id}
			</script>
			""")
	public int returnBook(int id);
//
//	@Update("""
//			<script>
//			UPDATE article
//			SET hitCount = hitCount + 1
//			WHERE id = #{id}
//			</script>
//			""")
//	public int increaseHitCount(int id);
//
//	@Select("""
//			<script>
//			SELECT hitCount
//			FROM article
//			WHERE id = #{id}
//			</script>
//			""")
//	public int getArticleHitCount(int id);
//
//	@Update("""
//			<script>
//			UPDATE article
//			SET goodReactionPoint = goodReactionPoint + 1
//			WHERE id = #{id}
//			</script>
//			""")
//	public int increaseGoodReactionPoint(int id);
//
//	@Update("""
//			<script>
//			UPDATE article
//			SET badReactionPoint = badReactionPoint + 1
//			WHERE id = #{id}
//			</script>
//			""")
//	public int increaseBadReactionPoint(int id);
//
//	@Update("""
//			<script>
//			UPDATE article
//			SET goodReactionPoint = goodReactionPoint - 1
//			WHERE id = #{id}
//			</script>
//			""")
//	public int decreaseGoodReactionPoint(int id);
//
//	@Update("""
//			<script>
//			UPDATE article
//			SET badReactionPoint = badReactionPoint - 1
//			WHERE id = #{id}
//			</script>
//			""")
//	public int decreaseBadReactionPoint(int id);
//	
//	@Select("""
//			<script>
//			SELECT *
//			FROM article
//			WHERE id = #{id}
//			</script>
//			""")
//	public Article getArticle(int id);
}