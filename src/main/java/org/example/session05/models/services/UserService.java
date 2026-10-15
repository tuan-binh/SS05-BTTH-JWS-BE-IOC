package org.example.session05.models.services;

import lombok.RequiredArgsConstructor;
import org.example.session05.models.dto.res.UserInfo;
import org.example.session05.models.dto.res.UserRes;
import org.example.session05.models.entities.User;
import org.example.session05.models.repositories.UserRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAllByName(String search) {
        return userRepository.searchNameInUser(search);
    }

    // Tác dụng: Phân trang danh sách user vd: tổng 6 giờ muốn phần trang là 3 phần tử 1 trang
    public Page<User> findAll(int page, int size,String sort,String direction) {

        // Chưa thông tin về các thông số của phân trang gồm
        /*
            page - trang hiện tại tính từ 0
            size - số lượng phần tử của 1 trang
            sort - sắp xếp theo field nào
            direction - hướng sắp xếp (ASC, DESC)
        */
        Sort dataSort;
        if(direction.equalsIgnoreCase("asc")) {
            dataSort = Sort.by(Sort.Direction.ASC,sort);
        } else {
            dataSort = Sort.by(Sort.Direction.DESC,sort);
        }

        Pageable pageable = PageRequest.of(page, size, dataSort);

        // Để phân trang được chúng ta phải sử dụng đối tượng Pageable

        // Repository cần pageable để phân trang được
        return userRepository.findAll(pageable);
    }

    // Hỗ trợ
    public Slice<User> sliceContent(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // Không có
        // totalElements - tổng số phần tử của toàn bộ trang
        // totalPages - tổng số trang
        Slice<User> slice = userRepository.findAllByAge(18,pageable);

        return slice;
    }

    public Page<User> paginationWithPageableDefault(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // BÀI TOÁN lấy ra top 3 người có tuổi thấp nhất
    public List<User> getTop3LessthenAge() {
        return userRepository.findTop3ByOrderByAgeAsc();
    }

    public List<UserInfo> getAllDTOWithAge() {
        return userRepository.findAllByAge(19);
    }

    public List<UserRes> getAllDTO() {
        return userRepository.getAllDTO();
    }

}
