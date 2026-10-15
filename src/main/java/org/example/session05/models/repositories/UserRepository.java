package org.example.session05.models.repositories;

import org.example.session05.models.dto.res.UserInfo;
import org.example.session05.models.dto.res.UserRes;
import org.example.session05.models.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    // Cú pháp: Hành động + By + Field + Condition

    // Query Method
    // --> SQL: SELECT * FROM users WHERE name LIKE {SEARCH}
//    List<User> findAllByNameContains(String search);

    // JPQL
    @Query("""
            SELECT u FROM User u WHERE u.name LIKE concat('%',:search,'%') 
        """)

    // Query Method dành cho những cái căn cơ bản có thể giải được
    // Custom Query dành cho khó nhằn
    List<User> searchNameInUser(@Param("search") String search);

    Slice<User> findAllByAge(Integer age,Pageable pageable);

    List<User> findTop3ByOrderByAgeAsc();

    // 2 loại first và top

    // find + [top | first] + (giá trị muốn giới hạn) + OrderBy + [Thuộc tính muốn sắp xếp] + [Hướng sắp xếp]

    // interface base projection (lưu ý: sử dụng ko cần @Query)
    List<UserInfo> findAllByAge(Integer age);

    // class base projection (lưu ý: phải sử dụng @Query)
    @Query("""
                SELECT 
                new org.example.session05.models.dto.res.UserRes(u.id,u.name,u.email)
                FROM User u
            """)
    List<UserRes> getAllDTO();

}
