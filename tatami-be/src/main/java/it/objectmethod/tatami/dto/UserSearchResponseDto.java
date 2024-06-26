package it.objectmethod.tatami.dto;

import java.util.Date;

import com.sun.istack.NotNull;

import it.objectmethod.tatami.entity.enums.UserStatus;
import lombok.Data;

@Data
public class UserSearchResponseDto {
	private Long id;
	@NotNull
	private String username;
	@NotNull
	private String password;
	private String newPassword;
	@NotNull
	private String nickname;
	@NotNull
	private String email;
	private UserStatus userStatus;
	private Date lastOnline;
	private byte[] profileImage;
	private Integer commonFriends;
}
