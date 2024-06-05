package it.objectmethod.tatami.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import it.objectmethod.tatami.controller.dto.LobbySearchQueryParams;
import it.objectmethod.tatami.controller.dto.UserSearchQueryParams;
import it.objectmethod.tatami.dto.UserSearchResponseDto;
import it.objectmethod.tatami.entity.Lobby;
import it.objectmethod.tatami.utils.QueryConstants;
import it.objectmethod.tatami.utils.RowMappers;
import it.objectmethod.tatami.utils.Utils;

@Repository
public class CustomRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final RowMapper<Lobby> LOBBY_ROW_MAPPER = RowMappers.getLobbyRowMapper();
	private static final RowMapper<UserSearchResponseDto> USER_SEARCH_ROW_MAPPER = RowMappers.getUserSearchRowMapper();

	@SuppressWarnings("deprecation")
	public List<Lobby> searchLobbiesPaged(LobbySearchQueryParams params) {
		if (params == null) {
			return null;
		}
		Integer offset = Integer.valueOf(params.getPage().intValue() * params.getSize().intValue());
		List<Object> args = new ArrayList<>();
		String query = QueryConstants.LOBBY_SEARCH[0];
		args.add(params.getUserId());
		args.add(params.getUserId());
		args.add(params.getUserId());
		args.add(params.getUserId());
		query = this.addLobbyAndClause(query, args, params);
		query += QueryConstants.LOBBY_SEARCH[1];
		args.add(offset);
		args.add(params.getSize());
		return jdbcTemplate.query(query, args.toArray(), LOBBY_ROW_MAPPER);
	}

	private String addLobbyAndClause(String query, List<Object> args, LobbySearchQueryParams params) {
		if (!Utils.isBlank(params.getLobbyType())) {
			query += "  AND lob.lobby_type = ? \n";
			args.add(params.getLobbyType());
		}
		if (!Utils.isBlank(params.getName())) {
			query += "  AND lob.lobby_name LIKE CONCAT('%', ?, '%') \n";
			args.add(params.getLobbyType());
		}
		return query;
	}

	@SuppressWarnings("deprecation")
	public List<UserSearchResponseDto> searchForFriends(UserSearchQueryParams params) {
		if (params == null) {
			return null;
		}
		Integer offset = Integer.valueOf(params.getPage().intValue() * params.getSize().intValue());
		List<Object> args = new ArrayList<>();
		String query = QueryConstants.USER_SEARCH[0];
		args.add(params.getUserId());
		args.add(params.getUserId());
		args.add(params.getUserId());
		args.add(params.getUserId());
		args.add(params.getUserId());
		query = this.addSearchUserAndClause(query, args, params);
		query += QueryConstants.USER_SEARCH[1];
		args.add(offset);
		args.add(params.getSize());
		return jdbcTemplate.query(query, args.toArray(), USER_SEARCH_ROW_MAPPER);
	}

	private String addSearchUserAndClause(String query, List<Object> args, UserSearchQueryParams params) {
		if (!Utils.isBlank(params.getNickname())) {
			query += "  AND u.nickname LIKE CONCAT('%', ?, '%') \n";
			args.add(params.getNickname());
		}
		if (!Utils.isBlank(params.getUsername())) {
			query += "  AND u.username = ? \n";
			args.add(params.getUsername());
		}
		return query;
	}
}
