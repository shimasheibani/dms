package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.dms.enums.UserRole;

import java.util.List;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int status;
    private String message;

    private UserRole userRole;
    private String email;
    private String password;
    private String Token;
    private String expirationDate;

    private UsersDto usersDto;
    private List<UsersDto> usersDtos;
}
