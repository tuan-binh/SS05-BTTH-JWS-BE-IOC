package org.example.session05.models.dto.res;

import org.springframework.beans.factory.annotation.Value;

public interface UserInfo {
    String getName();
    String getEmail();
    Integer getAge();

    @Value("#{target.name+' - '+target.email+' - '+target.age}")
    String getInformation();
}
