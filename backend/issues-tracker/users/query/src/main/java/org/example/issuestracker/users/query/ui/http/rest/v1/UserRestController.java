package org.example.issuestracker.users.query.ui.http.rest.v1;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.query.dispatcher.QueryDispatcher;
import org.example.issuestracker.shared.readmodel.ListUser;
import org.example.issuestracker.users.query.ui.http.rest.v1.mapper.GetListUsersParamMapper;
import org.example.issuestracker.users.query.ui.http.rest.v1.param.GetListUsersParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-management")
@RequiredArgsConstructor
public class UserRestController {
    private final QueryDispatcher queryDispatcher;

    @GetMapping("/users")
    public ResponseEntity<List<ListUser>> getListUsers(GetListUsersParam param) {
        var getListUsersQuery = GetListUsersParamMapper.toQuery(param);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(queryDispatcher.dispatch(getListUsersQuery));
    }
}
